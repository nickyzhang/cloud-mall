package com.cloud.api;

import com.cloud.common.core.bean.ResponseResult;
import com.cloud.service.MailService;
import com.cloud.vo.notification.CheckMailCodeParam;
import com.cloud.vo.notification.MailCodeParam;
import com.cloud.vo.notification.MailParam;
import com.cloud.vo.notification.RemoveMailCodeParam;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.mail.MessagingException;

@RestController
public class MailController {

    @Autowired
    MailService mailService;

    @ApiOperation(value = "发送普通邮件")
    @PostMapping("/mail/send")
    public ResponseResult send(@RequestBody MailParam mailParam) {
        ResponseResult result = new ResponseResult();
        if (mailParam == null) {
            return result.failed("发送的参数不能为空");
        }

        if (StringUtils.isBlank(mailParam.getEmail())) {
            return result.failed("发送的邮件地址不能为空");
        }

        if (StringUtils.isBlank(mailParam.getSubject())) {
            return result.failed("发送的邮件主题不能为空");
        }

        if (StringUtils.isBlank(mailParam.getContent())) {
            return result.failed("发送的邮件内容不能为空");
        }

        try {
            this.mailService.send(mailParam.getEmail(),mailParam.getSubject(),
                    mailParam.getContent(),mailParam.isHtml());
        } catch (Exception e) {
            result.failed("邮件发送失败");
        }
        return result.success("邮件发送成功");
    }

    @ApiOperation(value = "发送邮件验证码")
    @PostMapping("/mail/sendCode")
    public ResponseResult sendMailCode(@RequestBody MailCodeParam mailCodeParam) {
        ResponseResult result = new ResponseResult();
        if (mailCodeParam == null) {
            return result.failed("发送的参数不能为空");
        }

        if (StringUtils.isBlank(mailCodeParam.getSource())) {
            return result.failed("发送源不能为空");
        }

        if (StringUtils.isBlank(mailCodeParam.getEmail())) {
            return result.failed("发送的邮件地址不能为空");
        }

        if (StringUtils.isBlank(mailCodeParam.getCode())) {
            return result.failed("发送的邮件验证码不能为空");
        }

        try {
            this.mailService.sendVerifyCode(mailCodeParam.getSource(),mailCodeParam.getEmail(),
                    mailCodeParam.getSubject(),mailCodeParam.getCode());
        } catch (MessagingException e) {
            return result.failed("发送邮件验证码失败");
        }
        return result.success("发送邮件验证码成功");
    }

    @ApiOperation(value = "校验邮件验证码")
    @PostMapping("/mail/checkCode")
    public ResponseResult checkMailCode(@RequestBody CheckMailCodeParam checkMailCodeParam) {
        ResponseResult result = new ResponseResult();
        if (checkMailCodeParam == null) {
            return result.failed("校验邮件验证码参数为空");
        }

        String source = checkMailCodeParam.getSource();
        if (StringUtils.isBlank(source)) {
            return result.failed("发送源不能为空");
        }

        String email = checkMailCodeParam.getEmail();
        if (StringUtils.isBlank(email)) {
            return result.failed("发送的邮件地址不能为空");
        }

        String code = checkMailCodeParam.getCode();
        if (StringUtils.isBlank(code)) {
            return result.failed("发送的邮件验证码不能为空");
        }

        boolean checked = this.mailService.checkVerifyCode(source,email,code);
        return checked ? result.success("邮件验证码校验成功") : result.failed("邮件验证码校验失败");
    }

    @ApiOperation(value = "删除缓存的校验码")
    @PostMapping("/mail/removeCode")
    public ResponseResult removeMailCode(@RequestBody RemoveMailCodeParam removeMailCodeParam) {
        ResponseResult result = new ResponseResult();
        if (removeMailCodeParam == null) {
            return result.failed("删除校验邮件验证码参数为空");
        }

        String source = removeMailCodeParam.getSource();
        if (StringUtils.isBlank(source)) {
            return result.failed("发送源不能为空");
        }

        String email = removeMailCodeParam.getEmail();
        if (StringUtils.isBlank(email)) {
            return result.failed("邮件地址不能为空");
        }

        try {
            this.mailService.validateVerifyCode(source,email);
        } catch (Exception e) {
            return result.failed("删除验证码失败");
        }
        return result.success("删除邮箱校验码成功");
    }
}
