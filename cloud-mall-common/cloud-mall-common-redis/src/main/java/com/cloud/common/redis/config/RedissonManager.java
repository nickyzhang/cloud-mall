package com.cloud.common.redis.config;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RedissonManager {

//    @Value("${spring.redis.cluster.nodes}")
//    private String cluster;
//
//    @Value("${spring.redis.password}")
//    private String password;

    /**
     * 单机的Redission
     * @return
     */
    @Bean
    public RedissonClient getRedisson() {
        Config config = new Config();
        config.useSingleServer().setAddress("redis://192.168.3.101:6379");
        RedissonClient redissionClient = Redisson.create(config);
        return  redissionClient ;
    }

    /**
     * 集群配置的Redission
     * @return
     */
//    public RedissonClient getRedissonClsuter(){
//        String[] nodes = cluster.split(",");
//        //redisson版本是3.5，集群的ip前面要加上“redis://”，不然会报错，3.2版本可不加
//        for(int i=0;i<nodes.length;i++){
//            nodes[i] = "redis://"+nodes[i];
//        }
//        RedissonClient redisson = null;
//        Config config = new Config();
//        config.useClusterServers() //这是用的集群server
//                .setScanInterval(2000) //设置集群状态扫描时间
//                .addNodeAddress(nodes)
//                .setPassword(password);
//        redisson = Redisson.create(config);
//        return redisson;
//    }
}
