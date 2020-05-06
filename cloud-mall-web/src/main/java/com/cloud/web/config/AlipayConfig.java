package com.cloud.web.config;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Setter
@Getter
@Configuration
@PropertySource("classpath:alipay.properties")
public class AlipayConfig {

    @Value("${alipay.app_id}")
    private String appId;

    @Value("${alipay.sign_type}")
    private String signType;

    @Value("${alipay.format}")
    private String format;

    @Value("${alipay.charset}")
    private String charset;

    @Value("${alipay.app_private_key}")
    private String privateKey;

    @Value("${alipay.app_public_key}")
    private String publicKey;

    @Value("${alipay.ali_public_key}")
    private String aliPublicKey;

    @Value("${alipay.url}")
    private String url;

    @Value("${alipay.notify_url}")
    private String notifyUrl;

    @Value("${alipay.return_url}")
    private String returnUrl;

    @Value("${alipay.seller_id}")
    private String sellerId;

    @Value("${alipay.log_path}")
    private String logPath;

    @Bean
    public AlipayClient alipayClient() {

        return new DefaultAlipayClient(this.url, this.appId, this.privateKey,
                this.format, this.charset, this.aliPublicKey, this.signType);
    }
}
