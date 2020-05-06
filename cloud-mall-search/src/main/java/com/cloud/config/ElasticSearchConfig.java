package com.cloud.config;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpHost;
import org.apache.http.client.config.RequestConfig;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ElasticSearchConfig {

    @Value("${elasticsearch.cluster-name}")
    private String clusterName;

    @Value("${elasticsearch.cluster-host}")
    private String clusterHost;

    @Value("${elasticsearch.port}")
    private Integer port;

    @Value("${elasticsearch.scheme}")
    private String scheme;

    @Bean
    RestHighLevelClient restHighLevelClient() {
        String[] hosts = StringUtils.split(this.clusterHost,",");
        if (ArrayUtils.isEmpty(hosts)) {
            return null;
        }
        HttpHost[] httpHosts = new HttpHost[hosts.length];
        for (int i = 0; i < hosts.length; i++) {
            httpHosts[i] = new HttpHost(hosts[i],port,scheme);
        }
        RestClientBuilder builder = RestClient.builder(httpHosts);
        // 最好设置大一点，默认30秒，如果设置小了，结果还没有返回，就不返回数据了
        builder.setMaxRetryTimeoutMillis(60 * 1000);
        builder.setRequestConfigCallback(new RestClientBuilder.RequestConfigCallback() {
            @Override
            public RequestConfig.Builder customizeRequestConfig(RequestConfig.Builder requestConfigBuilder) {
                return requestConfigBuilder
                        .setConnectTimeout(5 * 1000)
                        .setSocketTimeout(15 * 10000);
            }
        });
        return new RestHighLevelClient(builder);
    }


}
