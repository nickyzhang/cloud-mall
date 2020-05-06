package com.cloud.api.fallback;

import com.cloud.api.NotificationService;
import com.cloud.common.core.bean.ResponseResult;
import com.cloud.vo.notification.*;
import feign.hystrix.FallbackFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class NotificationServiceFallbackFactory implements FallbackFactory<NotificationService>{

    @Override
    public NotificationService create(Throwable throwable) {
        log.warn("获取通知服务异常,开始回退",throwable);
        return new NotificationService() {
            @Override
            public ResponseResult send(MailParam mailParam) {
                return null;
            }

            @Override
            public ResponseResult sendMailCode(MailCodeParam mailCodeParam) {
                return null;
            }

            @Override
            public ResponseResult checkMailCode(CheckMailCodeParam checkMailCodeParam) {
                return null;
            }

            @Override
            public ResponseResult removeMailCode(RemoveMailCodeParam removeMailCodeParam) {
                return null;
            }

            @Override
            public ResponseResult sendSmsCode(SmsCodeParam smsCodeParam) {
                return null;
            }

            @Override
            public ResponseResult sendRegisterNotification(SmsParam smsParam) {
                return null;
            }

            @Override
            public ResponseResult sendUpdatePasswordNotification(SmsParam smsParam) {
                return null;
            }

            @Override
            public ResponseResult checkSmsCode(CheckSmsCodeParam checkSmsCodeParam) {
                return null;
            }

            @Override
            public ResponseResult removeSmsCode(RemoveSmsCodeParam removeSmsCodeParam) {
                return null;
            }
        };
    }
}
