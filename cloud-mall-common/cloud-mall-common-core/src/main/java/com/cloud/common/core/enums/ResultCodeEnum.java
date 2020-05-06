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
    USER_LOGIN_FAILED(20000000,"账号或者密码不正确"),
    USER_REGISTER_FAILED(20000001,"用户注册失败"),
    USER_UPDATE_PASSWORD_FAILED(20000002,"用户更新密码失败"),
    USER_EMPTY_NAME(21000000,"用户名为空"),
    USER_EMPTY_PASSWORD(21000001,"密码为空"),
    USER_EMPTY_PHONE(21000002,"手机号为空"),
    USER_EMPTY_EMAIL(21000003,"邮箱为空"),
    USER_DIFFERENT_PASSWORD(21000004,"两次输入的密码不一样"),
    USER_EMPTY_VERIFY_CODE(21000005,"验证码为空"),
    USER_VERIFY_CODE_ERROR(21000006,"错误的验证码"),
    USER_SEND_CODE_FAILED(21000007,"发送验证码失败"),
    USER_SEND_MAIL_FAILED(21000008,"发送邮件失败"),
    USER_EMPTY_NEW_PASSWORD(210000009,"新密码为空"),
    USER_EMPTY_AUTH_CODE(210000010,"授权码为空"),
    USER_AUTH_CODE_ERROR(210000011,"错误的授权码"),
    USER_EMPTY_MESSAGE(210000012,"发送消息为空"),
    SOCIAL_CONNECT_FAILED(22000001,"社交账号连接失败, 服务提供商=%s"),
    SOCIAL_GET_ACCESS_TOKEN_FAIDED(22000002,"社交登录获取Access Token失败, 服务提供商=%s"),
    SOCIAL_GET_REFRESH_TOKEN_FAIDED(22000003,"社交登录获取Refresh Token失败, 服务提供商=%s"),
    SOCIAL_GET_OPEN_ID_FAIDED(22000004,"社交登录获取用户openid失败, 服务提供商=%s"),
    SOCIAL_GET_USER_FAILED(22000005,"获取社交用户信息失败, 服务提供商=%s"),
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
    PAYMENT_ALI_FAIL(520000002,"%s支付失败"),
    INVENTORY_NULL(410000001,"库存对象为空"),
    INVENTORY_ADD_FAILED(410000002,"添加库存失败"),
    INVENTORY_ADD_NUM_LESS_THAN_ZERO(410000003,"添加库存数量小于0"),
    INVENTORY_UPDATE_FAILED(410000004,"更新库存失败"),
    INVENTORY_DELETE_FAILED(410000005,"删除库存失败"),
    INVENTORY_INSUFFICIENT(410000006,"库存数量不足"),
    INVENTORY_AVAILABLE_INSUFFICIENT(410000007,"可用库存数量不足"),
    INVENTORY_FROZEN_INSUFFICIENT(410000008,"冻结库存数量不足"),
    INVENTORY_ADD_LESS_THAN_ZERO(410000009,"添加的库存数量小于0"),
    INVENTORY_DEDUCT_LESS_THAN_ZERO(410000010,"扣减库存数量小于0"),
    INVENTORY_DEDUCT_FAILED(410000011,"扣减库存失败"),
    INVENTORY_FLOW_NULL(420000001,"库存流水对象为空"),
    INVENTORY_FLOW_ADD_FAILED(420000002,"添加库存失败"),
    INVENTORY_FLOW_UPDATE_FAILED(420000003,"添加库存失败"),
    INVENTORY_FLOW_DELETE_FAILED(420000004,"删除库存失败"),
    INVENTORY_UPLOAD_LOG_NULL(430000001,"库存上传日志对象为空"),
    INVENTORY_UPLOAD_LOG_ADD_FAILED(430000002,"添加库存上传日志失败"),
    INVENTORY_UPLOAD_LOG_UPDATE_FAILED(430000003,"更新库存上传日志失败"),
    INVENTORY_UPLOAD_LOG_DELETE_FAILED(430000004,"删除库存上传日志失败"),
    INVENTORY_REMINDER_NULL(440000001,"库存警告提醒对象为空"),
    INVENTORY_REMINDER_ADD_FAILED(440000002,"添加库存警告提醒失败"),
    INVENTORY_REMINDER_UPDATE_FAILED(440000003,"更新库存警告提醒失败"),
    INVENTORY_REMINDER_DELETE_FAILED(440000004,"删除库存警告提醒失败"),
    PROMOTION_ACTIVITY_STATUS_ADD_FAILEF(500000001,"活动状态添加失败"),
    PROMOTION_ACTIVITY_STATUS_UPDATE_FAILEF(500000002,"活动状态更新失败"),
    PROMOTION_TEMPLATE_STATUS_ADD_FAILEF(510000001,"模板状态添加失败"),
    PROMOTION_TEMPLATE_STATUS_UPDATE_FAILEF(510000002,"模板状态更新失败"),
    CART_ADD_FAILED(600000001,"添加到购物车失败"),
    ORDER_NULL(700000001,"提交的订单参数为空"),
    ORDER_USER_NOT_LOGIN(700000010,"用户未登录");
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
