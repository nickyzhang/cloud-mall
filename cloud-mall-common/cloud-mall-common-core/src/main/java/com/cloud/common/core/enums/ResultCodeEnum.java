package com.cloud.common.core.enums;

import java.util.HashMap;
import java.util.Map;

public enum ResultCodeEnum {
    OK(200, "成功"),
    MOVED_PERMANENTLY(301, "永久重定向"),
    MOVED_TEMPORARILY(302, "临时重定向"),
    NOT_MODIFIED(304, "没未修改"),
    BAD_REQUEST(400, "坏的请求"),
    AUTHORIZED_FAILED(401,"认证失败"),
    FORBIDDEN_EXCEPTION(403,"没有权限访问"),
    NOT_FOUND(404,"未发现"),
    REQUEST_TIMEOUT(408, "请求超时"),
    CONFLICT(409, "冲突"),
    INTERNAL_SERVER_ERROR(500,"服务器内部异常"),
    ILLEGAL_ARGUMENT_EXCEPTION(10000101,"参数非法异常"),
    NULL_POINT_EXCEPTION(10000102,"空指针异常"),
    VALIDATE_PARAMS_FAILED(10000201,"参数校验失败"),
    USER_LOGIN_FAILED(20000001,"账号或者密码不正确"),
    USER_EMPTY_NAME(20000002,"用户名为空"),
    USER_EMPTY_PASSWORD(20000003,"密码为空"),
    USER_EMPTY_PHONE(20000004,"手机号为空"),
    USER_DIFFERENT_PASSWORD(20000005,"两次输入的密码不一样"),
    USER_EMPTY_VERIFY_CODE(20000006,"验证码为空"),
    USER_VERIFY_CODE_ERROR(20000007,"错误的验证码"),
    USER_SEND_CODE_FAILED(20000008,"发送验证码失败"),
    USER_SEND_MAIL_FAILED(20000009,"发送邮件失败"),
    USER_EMPTY_NEW_PASSWORD(20000010,"新密码为空"),
    USER_EMPTY_AUTH_CODE(200000011,"授权码为空"),
    USER_AUTH_CODE_ERROR(200000012,"错误的授权码"),
    USER_EMPTY_MESSAGE(200000013,"发送消息为空"),
    SOCIAL_CONNECT_FAILED(21000001,"社交账号连接失败, 服务提供商=%s"),
    SOCIAL_GET_ACCESS_TOKEN_FAIDED(21000002,"社交登录获取Access Token失败, 服务提供商=%s"),
    SOCIAL_GET_REFRESH_TOKEN_FAIDED(21000003,"社交登录获取Refresh Token失败, 服务提供商=%s"),
    SOCIAL_GET_OPEN_ID_FAIDED(21000004,"社交登录获取用户openid失败, 服务提供商=%s"),
    SOCIAL_GET_USER_FAILED(21000005,"获取社交用户信息失败, 服务提供商=%s"),
    NOTIFY_SMS_EMPTY_PHONE(30000000,"手机号码为空"),
    NOTIFY_SMS_INVALID_PHONE_PATTERN(30000001,"无效的手机号码格式"),
    NOTIFY_SMS_EMPTY_MESSAGE(30000002,"短信内容为空"),
    NOTIFY_SMS_SEND_EXCEPTION(30000003,"客户端异常,发送短信失败"),
    NOTIFY_MAIL_EMPTY_TO(31000000,"收件人地址为空"),
    NOTIFY_MAIL_EMPTY_FROM(31000001,"发件人地址为空"),
    NOTIFY_MAIL_INVALID_ADDRESS(31000002,"无效的邮件地址"),
    NOTIFY_MAIL_EMPTY_CONTENT(31000003,"无效的邮件内容"),
    NOTIFY_MAIL_SEND_EXCEPTION(31000004,"客户端异常,发送邮件失败"),
    PAYMENT_WX_EXCEPTION(510000001,"%s支付异常"),
    PAYMENT_WX_FAIL(510000002,"%s支付失败"),
    PAYMENT_ALI_EXCEPTION(520000001,"%s支付异常"),
    PAYMENT_ALI_FAIL(520000002,"%s支付失败");

    private int code;

    private String message;

    private static Map<Integer, ResultCodeEnum> codeMap;

    ResultCodeEnum(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public static ResultCodeEnum getEnum(int code) {
        for (ResultCodeEnum element : ResultCodeEnum.values()) {
            if (element.code == code) {
                return element;
            }
        }
        return null;
    }

    static {
        codeMap = new HashMap<>(16);
        for (ResultCodeEnum element : ResultCodeEnum.values()) {
            codeMap.put(element.getCode(),element);
        }
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
