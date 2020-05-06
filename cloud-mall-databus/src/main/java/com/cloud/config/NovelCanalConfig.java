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
public class NovelCanalConfig {

    @Value("${canal.zk-servers}")
    private String zkServers;

    @Value("${canal.novel.host}")
    private String hostname;

    @Value("${canal.novel.port}")
    private int port;

    @Value("${canal.novel.destination}")
    private String destination;

    @Value("${canal.novel.username}")
    private String username;

    @Value("${canal.novel.password}")
    private String password;

    @Value("${canal.novel.batch-size}")
    private int batchSize;

    @Bean(name = "novelConnector")
    public CanalConnector getConnector() {
        if (StringUtils.isNotBlank(this.zkServers)) {
            return CanalConnectors.newClusterConnector(zkServers,destination,username,password);
        }
        return CanalConnectors.newSingleConnector(
                new InetSocketAddress(this.hostname, this.port),
                this.destination, this.username, this.password);
    }
}
