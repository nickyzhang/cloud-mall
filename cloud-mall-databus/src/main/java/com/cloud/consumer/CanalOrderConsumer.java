package com.cloud.consumer;

import com.cloud.common.core.utils.JSONUtils;
import com.cloud.handler.CatalogCacheHandler;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Component;

import java.util.Map;

@Data
@Slf4j
@Component
public class CanalOrderConsumer {

    @Autowired
    CatalogCacheHandler catalogCacheHandler;

    @KafkaListener(topics = "samples")
    public void index(ConsumerRecord<?, ?> record, Acknowledgment ack) throws Exception{
        Map<String,Object> results = JSONUtils.jsonToMap((String)record.value());
        System.out.println("[CanalOrderConsumer]: "+ results);
        ack.acknowledge();
    }

}
