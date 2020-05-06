package com.cloud.service.impl;

import com.aliyuncs.CommonRequest;
import com.aliyuncs.CommonResponse;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.http.MethodType;
import com.cloud.common.cache.service.CacheService;
import com.cloud.common.core.constants.SymbolConstants;
import com.cloud.config.AliyunSmsConfig;
import com.cloud.service.SmsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class SmsServiceImpl implements SmsService {

    @Autowired
    IAcsClient client;

    @Autowired
    AliyunSmsConfig aliyunSmsConfig;

    @Autowired
    CacheService cacheService;

    @Override
    public void sendVerifyCode(String source, String phone, String code) throws Exception {
        CommonRequest request = buildCommonRequest("SendSms",this.aliyunSmsConfig.getSignName(),
                this.aliyunSmsConfig.getVerifyCodeTemplateNode(),phone, code);
        CommonResponse response = client.getCommonResponse(request);
        log.info("[sendVerifyCode] 向{}发送验证码{},状态:{}",phone,code,response.getHttpStatus());
        StringBuilder sb = new StringBuilder();
        sb.append(source).append(SymbolConstants.COLON_SYMBOL).append(phone);
        this.cacheService.cachePut("SmsCodeCache",sb.toString(),code);
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
        CommonResponse response = client.getCommonResponse(request);
        log.info("[sendRegisterNotification] 发送状态: {}",response.getHttpStatus());
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
        CommonResponse response =  client.getCommonResponse(request);
        log.info("[sendUpdatePasswordNotification] 发送状态: {}",response.getHttpStatus());
    }

    /**
     * 校验短信验证码
     * @param source
     * @param phone
     * @param code
     * @return
     */
    @Override
    public boolean checkVerifyCode(String source, String phone, String code) {
        StringBuilder sb = new StringBuilder();
        sb.append(source).append(SymbolConstants.COLON_SYMBOL).append(phone);
        String verifyCode = (String)this.cacheService.cacheResult("SmsCodeCache",sb.toString());
        return code.equals(verifyCode);
    }

    /**
     * 从缓存中删除校验码
     * @param source
     * @param phone
     */
    @Override
    public void validateVerifyCode(String source, String phone) {
        StringBuilder sb = new StringBuilder();
        sb.append(source).append(SymbolConstants.COLON_SYMBOL).append(phone);
        this.cacheService.cacheRemove("SmsCodeCache",sb.toString());
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
