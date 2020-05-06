package com.cloud.mq.kafka.service;

import com.alibaba.fastjson.JSONObject;
import com.cloud.common.core.bean.Message;
import com.cloud.common.core.bean.ResponseResult;
import com.cloud.common.core.utils.JSONUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFuture;

@Slf4j
@Service
public class KafkaSender {

    @Autowired
    KafkaTemplate template;

    public void send(String topic, Object msg) {
        ListenableFuture<SendResult> future = template.send(topic,msg);
        future.addCallback(
                result -> log.info("消息发送成功：{}", JSONUtils.objectToJson(msg)),
                throwable -> log.error("消息发送失败：{}", JSONUtils.objectToJson(msg))
        );
    }

    public void send(String topic, String key, Object msg) {
        ListenableFuture<SendResult> future = template.send(topic,key,msg);
        future.addCallback(
                result -> log.info("消息发送成功：{}",JSONUtils.objectToJson(msg)),
                throwable -> log.error("消息发送失败：{}",JSONUtils.objectToJson(msg))
        );
    }
}
