package com.cloud.api;

import com.cloud.common.core.bean.ResponseResult;
import com.cloud.service.SmsService;
import com.cloud.vo.notification.*;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class SmsController {
    @Autowired
    SmsService smsService;

    @ApiOperation(value = "发送手机验证码")
    @PostMapping("/sms/sendCode")
    public ResponseResult sendSmsCode(@RequestBody SmsCodeParam smsCodeParam) {
        ResponseResult result = new ResponseResult();
        if (smsCodeParam == null) {
            return result.failed("发送的参数不能为空");
        }

        String source = smsCodeParam.getSource();
        if (StringUtils.isBlank(source)) {
            return result.failed("发送源不能为空");
        }

        String mobile = smsCodeParam.getMobile();
        if (StringUtils.isBlank(mobile)) {
            return result.failed("发送的手机号码不能为空");
        }

        String code = smsCodeParam.getCode();
        if (StringUtils.isBlank(code)) {
            return result.failed("发送的手机验证码不能为空");
        }

        try {
            this.smsService.sendVerifyCode(source,mobile,code);
        } catch (Exception e) {
            return result.failed("发送手机验证码失败");
        }
        return result.success("发送手机验证码成功");
    }

    @ApiOperation(value = "发送注册成功通知")
    @PostMapping("/sms/sendRegNotification")
    public ResponseResult sendRegisterNotification(@RequestBody SmsParam smsParam) {
        ResponseResult result = new ResponseResult();
        if (smsParam == null) {
            return result.failed("发送的短消息参数不能为空");
        }

        String source = smsParam.getSource();
        if (StringUtils.isBlank(source)) {
            return result.failed("发送源不能为空");
        }

        String mobile = smsParam.getMobile();
        if (StringUtils.isBlank(mobile)) {
            return result.failed("发送的手机号码不能为空");
        }

        String message = smsParam.getMessage();
        if (StringUtils.isBlank(message)) {
            return result.failed("发送的消息不能为空");
        }

        try {
            this.smsService.sendRegisterNotification(mobile,message);
        } catch (Exception e) {
            return result.failed("注册成功通知发送失败");
        }
        return result.success("发送注册成功通知成功");
    }

    @ApiOperation(value = "发送更新密码成功通知")
    @GetMapping("/sms/sendModifyPasswdNotification")
    public ResponseResult sendUpdatePasswordNotification(@RequestBody SmsParam smsParam) {
        ResponseResult result = new ResponseResult();
        if (smsParam == null) {
            return result.failed("发送的短消息参数不能为空");
        }

        String source = smsParam.getSource();
        if (StringUtils.isBlank(source)) {
            return result.failed("发送源不能为空");
        }

        String mobile = smsParam.getMobile();
        if (StringUtils.isBlank(mobile)) {
            return result.failed("发送的手机号码不能为空");
        }

        String message = smsParam.getMessage();
        if (StringUtils.isBlank(message)) {
            return result.failed("发送的消息不能为空");
        }

        try {
            this.smsService.sendUpdatePasswordNotification(mobile,message);
        } catch (Exception e) {
            return result.failed("修改密码成功通知发送失败");
        }
        return result.success("发送修改密码成功通知成功");
    }

    @ApiOperation(value = "校验短信验证码")
    @PostMapping("/sms/checkCode")
    public ResponseResult checkMailCode(@RequestBody CheckSmsCodeParam checkSmsCodeParam) {
        ResponseResult result = new ResponseResult();
        if (checkSmsCodeParam == null) {
            return result.failed("校验短信验证码参数为空");
        }

        String source = checkSmsCodeParam.getSource();
        if (StringUtils.isBlank(source)) {
            return result.failed("发送源不能为空");
        }

        String mobile = checkSmsCodeParam.getMobile();
        if (StringUtils.isBlank(mobile)) {
            return result.failed("手机号码不能为空");
        }

        String code = checkSmsCodeParam.getCode();
        if (StringUtils.isBlank(code)) {
            return result.failed("短信验证码不能为空");
        }

        boolean checked = this.smsService.checkVerifyCode(source,mobile,code);
        return checked ? result.success("短信验证码校验成功") : result.failed("短信验证码校验失败");
    }

    @ApiOperation(value = "删除缓存的校验码")
    @PostMapping("/sms/removeCode")
    public ResponseResult removeMailCode(@RequestBody RemoveSmsCodeParam removeSmsCodeParam) {
        ResponseResult result = new ResponseResult();
        if (removeSmsCodeParam == null) {
            return result.failed("删除校验短信验证码参数为空");
        }

        String source = removeSmsCodeParam.getSource();
        if (StringUtils.isBlank(source)) {
            return result.failed("发送源不能为空");
        }

        String mobile = removeSmsCodeParam.getMobile();
        if (StringUtils.isBlank(mobile)) {
            return result.failed("手机号码不能为空");
        }

        try {
            this.smsService.validateVerifyCode(source,mobile);
        } catch (Exception e) {
            return result.failed("删除验证码失败");
        }
        return result.success("删除短信校验码成功");
    }
}
