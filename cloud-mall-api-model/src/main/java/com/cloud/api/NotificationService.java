package com.cloud.api;

import com.cloud.api.fallback.NotificationServiceFallbackFactory;
import com.cloud.common.core.bean.ResponseResult;
import com.cloud.vo.notification.*;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(value = "/catalogcloud-mall-notification",fallbackFactory = NotificationServiceFallbackFactory.class)
public interface NotificationService {

    @RequestMapping(value = "/catalog/mail/send", method = RequestMethod.POST)
    public ResponseResult send(@RequestBody MailParam mailParam);

    @RequestMapping(value = "/catalog/mail/sendCode", method = RequestMethod.POST)
    public ResponseResult sendMailCode(@RequestBody MailCodeParam mailCodeParam);

    @RequestMapping(value = "/catalog/mail/checkCode", method = RequestMethod.POST)
    public ResponseResult checkMailCode(@RequestBody CheckMailCodeParam checkMailCodeParam);

    @RequestMapping(value = "/catalog/mail/removeCode", method = RequestMethod.POST)
    public ResponseResult removeMailCode(@RequestBody RemoveMailCodeParam removeMailCodeParam);

    @RequestMapping(value = "/catalog/sms/sendCode", method = RequestMethod.POST)
    public ResponseResult sendSmsCode(@RequestBody SmsCodeParam smsCodeParam);

    @RequestMapping(value = "/catalog/sms/sendRegNotification", method = RequestMethod.POST)
    public ResponseResult sendRegisterNotification(@RequestBody SmsParam smsParam);

    @RequestMapping(value = "/catalog/sms/sendModifyPasswdNotification", method = RequestMethod.POST)
    public ResponseResult sendUpdatePasswordNotification(@RequestBody SmsParam smsParam);

    @RequestMapping(value = "/catalog/sms/checkCode", method = RequestMethod.POST)
    public ResponseResult checkSmsCode(@RequestBody CheckSmsCodeParam checkSmsCodeParam);

    @RequestMapping(value = "/catalog/sms/removeCode", method = RequestMethod.POST)
    public ResponseResult removeSmsCode(@RequestBody RemoveSmsCodeParam removeSmsCodeParam);
}
