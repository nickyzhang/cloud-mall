package com.cloud.common.notification.service.impl;

import com.aliyuncs.CommonRequest;
import com.aliyuncs.CommonResponse;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.http.MethodType;
import com.cloud.common.notification.config.AliyunSmsConfig;
import com.cloud.common.notification.service.SmsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("aliyunSmsService")
public class AliyunSmsService implements SmsService {

    @Autowired
    IAcsClient client;

    @Autowired
    AliyunSmsConfig aliyunSmsConfig;

    /**
     * 发送验证码短信
     * @param phone
     * @param msg
     */
    @Override
    public void sendVerifyCode(String phone, String msg) throws Exception{
        CommonRequest request = buildCommonRequest("SendSms",this.aliyunSmsConfig.getSignName(),
                this.aliyunSmsConfig.getVerifyCodeTemplateNode(),phone, msg);
        CommonResponse response = client.getCommonResponse(request);
        System.out.println(response.getHttpStatus()+" -> "+response.getData());
    }

    /**
     * 发送注册成功通知短信
     * @param phone
     * @param msg
     */
    @Override
    public void sendRegisterNotification(String phone, String msg) throws Exception {
        CommonRequest request = buildCommonRequest("SendSms",this.aliyunSmsConfig.getSignName(),
                this.aliyunSmsConfig.getRegisterNotifyTemplateNode(),phone, msg);
        client.getCommonResponse(request);
    }

    /**
     * 发送密码更新成功通知短信
     * @param phone
     * @param msg
     */
    @Override
    public void sendUpdatePasswordNotification(String phone, String msg) throws Exception {
        CommonRequest request = buildCommonRequest("SendSms",this.aliyunSmsConfig.getSignName(),
                this.aliyunSmsConfig.getUpdatePasswordNotifyTemplateNode(),phone, msg);
        client.getCommonResponse(request);
    }

    /**
     * 构建发送短信的请求
     * @param action
     * @param signName
     * @param templateCode
     * @param phoneNumber
     * @param templateParam
     * @return
     */
    private CommonRequest buildCommonRequest(
            String action, String signName,
            String templateCode, String phoneNumber,
            String templateParam) {
        CommonRequest request = new CommonRequest();
        request.setMethod(MethodType.POST);
        request.setDomain(this.aliyunSmsConfig.getDomain());
        request.setVersion(this.aliyunSmsConfig.getVersion());
        request.setAction(action);
        request.putQueryParameter("PhoneNumbers", phoneNumber);
        request.putQueryParameter("SignName", signName);
        request.putQueryParameter("TemplateCode", templateCode);
        request.putQueryParameter("TemplateParam", "{\"code\":\""+templateParam+"\"}");
        return request;
    }
}
