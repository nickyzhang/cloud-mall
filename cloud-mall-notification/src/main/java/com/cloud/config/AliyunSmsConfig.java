package com.cloud.config;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Getter
@Setter
@Configuration
@RefreshScope
public class AliyunSmsConfig {

    @Value("${aliyun.sms.api_key}")
    private String accessKeyId;

    @Value("${aliyun.sms.api_secret}")
    private String accessKeySecret;

    @Value("${aliyun.sms.domain}")
    private String domain;

    @Value("${aliyun.sms.version}")
    private String version;

    @Value("${aliyun.sms.verify_code_template_code}")
    private String verifyCodeTemplateNode;

    @Value("${aliyun.sms.register_notify_template_code}")
    private String registerNotifyTemplateNode;

    @Value("${aliyun.sms.update_password_notify_template_code}")
    private String updatePasswordNotifyTemplateNode;

    @Value("${aliyun.sms.sign_name}")
    private String signName;

    @Value("${aliyun.sms.product}")
    private String product;

    @Value("${aliyun.sms.region}")
    private String region;

    @Bean
    public IAcsClient iAcsClient() throws ClientException {

        // 初始化ascClient,不支持多region
        IClientProfile profile = DefaultProfile.getProfile(this.region, this.accessKeyId,
                this.accessKeySecret);
        DefaultProfile.addEndpoint(this.region, this.region, this.product, this.domain);

        IAcsClient acsClient = new DefaultAcsClient(profile);
        return acsClient;
    }
}