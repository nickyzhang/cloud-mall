package com.cloud.consumer;

import com.cloud.common.core.utils.JSONUtils;
import com.cloud.handler.CatalogCacheHandler;
import com.cloud.handler.CatalogIndexHandler;
import com.cloud.handler.DataHandler;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.MapUtils;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Component;

import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

@Data
@Slf4j
@Component
public class MaxwellCatalogConsumer {

    @Autowired
    CatalogIndexHandler catalogIndexHandler;

//    @KafkaListener(topics = "story")
    public void index(ConsumerRecord<?, ?> record, Acknowledgment ack) throws Exception{
        Map<String,Object> records = JSONUtils.jsonToMap((String)record.value());
        Map<String,String> row = (Map<String,String>)records.get("data");
        if (MapUtils.isNotEmpty(row)) {
            catalogIndexHandler.handle(row);
        }
        ack.acknowledge();
    }
}
