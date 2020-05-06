package com.cloud.config;

import com.alibaba.otter.canal.client.CanalConnector;
import com.alibaba.otter.canal.client.CanalConnectors;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.net.InetSocketAddress;

@Data
@Configuration
@RefreshScope
public class SamplesCanalConfig {

    @Value("${canal.zk-servers}")
    private String zkServers;

    @Value("${canal.samples.host}")
    private String hostname;

    @Value("${canal.samples.port}")
    private int port;

    @Value("${canal.samples.destination}")
    private String destination;

    @Value("${canal.samples.username}")
    private String username;

    @Value("${canal.samples.password}")
    private String password;

    @Value("${canal.samples.batch-size}")
    private int batchSize;

    @Bean(name = "samplesConnector")
    public CanalConnector getConnector() {
        if (StringUtils.isNotBlank(this.zkServers)) {
            return CanalConnectors.newClusterConnector(zkServers,destination,username,password);
        }
        return CanalConnectors.newSingleConnector(
                new InetSocketAddress(this.hostname, this.port),
                this.destination, this.username, this.password);
    }
}
