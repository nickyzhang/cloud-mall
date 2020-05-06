package com.cloud.consumer;

import com.cloud.common.core.utils.JSONUtils;
import com.cloud.handler.CatalogCacheHandler;
import com.cloud.handler.CatalogIndexHandler;
import com.cloud.handler.DataHandler;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.MapUtils;
import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Component;
import java.util.Map;
import java.util.Set;

@Data
@Slf4j
@Component
public class CanalCatalogConsumer {

    @Autowired
    CatalogCacheHandler catalogCacheHandler;

    @KafkaListener(topics = "novel")
    public void index(ConsumerRecord<?, ?> record, Acknowledgment ack) throws Exception{
        Map<String,Object> results = JSONUtils.jsonToMap((String)record.value());
        System.out.println("[CanalCatalogConsumer]: "+ results);
        ack.acknowledge();
    }


}
