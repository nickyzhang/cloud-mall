package com.cloud.common.core.utils;

import org.apache.commons.codec.binary.Base64;
import javax.crypto.Cipher;
import java.security.*;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

public class RSAUtil {

    /** 公钥 */
    public static String publicKey;
    /** 私钥 */
    public static String privateKey;

    /** 生成公钥和私钥 */
    public static void generateKey() {
        // 初始化秘钥
        KeyPairGenerator keyPairGenerator;
        try {
            keyPairGenerator = KeyPairGenerator.getInstance("RSA");
            // 产生一个安全的随机数生成器
            SecureRandom sr =  new SecureRandom();
            // 设置512位长的秘钥(64字节)
            keyPairGenerator.initialize(512, sr);
            // 开始创建
            KeyPair keyPair = keyPairGenerator.generateKeyPair();
            RSAPublicKey rsaPublicKey = (RSAPublicKey)keyPair.getPublic();
            RSAPrivateKey rsaPrivateKey = (RSAPrivateKey)keyPair.getPrivate();
            // 进行转码
            publicKey = Base64.encodeBase64String(rsaPublicKey.getEncoded());
            privateKey = Base64.encodeBase64String(rsaPrivateKey.getEncoded());
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }

    /**
     * 使用私钥进行加密和解密
     * @param content
     * @param privateKeyStr
     * @param mode
     * @return
     */
    public static String encryptByPrivateKey(String content, String privateKeyStr, int mode) {
        PKCS8EncodedKeySpec pkcs8EncodedKeySpec = new PKCS8EncodedKeySpec(Base64.decodeBase64(privateKeyStr));
        KeyFactory keyFactory;
        PrivateKey privateKey;
        Cipher cipher;
        byte[] result;
        String text = null;

        try {
            keyFactory = KeyFactory.getInstance("RSA");
            // 还原key对象
            privateKey = keyFactory.generatePrivate(pkcs8EncodedKeySpec);
            cipher = Cipher.getInstance("RSA");
            cipher.init(mode, privateKey);
            // 如果是加密模式
            if (mode == Cipher.ENCRYPT_MODE) {
                result = cipher.doFinal(content.getBytes());
                text = Base64.encodeBase64String(result);
            }
            // 如果是解密模式
            else if (mode == Cipher.DECRYPT_MODE) {
                result = cipher.doFinal(Base64.decodeBase64(content));
                text = new String(result, "UTF-8");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return text;
    }

    public static String encryptByPublicKey(String content, String publicKeyStr, int mode) {
        X509EncodedKeySpec x509EncodedKeySpec = new X509EncodedKeySpec(Base64.decodeBase64(publicKeyStr));
        KeyFactory keyFactory;
        PublicKey publicKey;
        Cipher cipher;
        byte[] result;
        String text = null;
        try {
            keyFactory = KeyFactory.getInstance("RSA");
            // 还原
            publicKey = keyFactory.generatePublic(x509EncodedKeySpec);
            cipher = Cipher.getInstance("RSA");
            cipher.init(mode, publicKey);
            if (mode == Cipher.ENCRYPT_MODE) { // 加密
                result = cipher.doFinal(content.getBytes());
                text = Base64.encodeBase64String(result);
            } else if (mode == Cipher.DECRYPT_MODE) { // 解密
                result = cipher.doFinal(Base64.decodeBase64(content));
                text = new String(result, "UTF-8");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return text;
    }

    /**
     * 使用RSA进行数字签名
     * @param content 内容
     * @param privateKeyStr 密钥
     * @return
     */
    public static byte[] sign(String content, String privateKeyStr) {
        PKCS8EncodedKeySpec pkcs8EncodedKeySpec = new PKCS8EncodedKeySpec(Base64.decodeBase64(privateKeyStr));
        KeyFactory keyFactory = null;
        PrivateKey privateKey = null;
        Signature signature = null;
        byte[] results = null;
        try {
            keyFactory = KeyFactory.getInstance("RSA");
            privateKey = keyFactory.generatePrivate(pkcs8EncodedKeySpec);
            signature = Signature.getInstance("MD5withRSA");
            signature.initSign(privateKey);
            signature.update(content.getBytes());
            results = signature.sign();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return results;
    }

    /**
     * 使用RSA验证数字签名
     * @param content
     * @param results
     * @param publicKeyStr
     * @return
     */
    public static boolean verify(String content, byte[] results, String publicKeyStr) {
        X509EncodedKeySpec x509EncodedKeySpec = new X509EncodedKeySpec(Base64.decodeBase64(publicKeyStr));
        KeyFactory keyFactory;
        PublicKey publicKey;
        Signature signature = null;
        boolean verified = Boolean.FALSE;
        try {
            keyFactory = KeyFactory.getInstance("RSA");
            // 还原
            publicKey = keyFactory.generatePublic(x509EncodedKeySpec);
            signature = Signature.getInstance("MD5withRSA");
            signature.initVerify(publicKey);
            signature.update(content.getBytes());
            verified = signature.verify(results);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return verified;
    }

    public static void main(String[] args) {
        RSAUtil.generateKey();
        System.out.println("公钥匙给接收方:" + RSAUtil.publicKey);
        System.out.println("私钥给发送方:" + RSAUtil.privateKey);
        System.out.println("-------------第二个栗子，公钥加密私钥解密-------------");
        String content = "Hello, RSA Signature";
        byte[] results = RSAUtil.sign(content, RSAUtil.privateKey);
        System.out.println("签名后的结果");
        boolean verified = RSAUtil.verify(content,results,RSAUtil.publicKey);
        System.out.println(verified);
    }
}
