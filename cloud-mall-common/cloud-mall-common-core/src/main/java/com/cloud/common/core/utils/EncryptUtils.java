package com.cloud.common.core.utils;

import org.apache.commons.codec.digest.Md5Crypt;
import org.mindrot.jbcrypt.BCrypt;

public class EncryptUtils {

    public static String genSalt(int saltLength) {

        return BCrypt.gensalt(saltLength);
    }

    public static String encrypt(String password, String salt) {

        return BCrypt.hashpw(password,salt);
    }


    public static String MD5(String data) {
        return Md5Crypt.apr1Crypt(data);
    }


}
