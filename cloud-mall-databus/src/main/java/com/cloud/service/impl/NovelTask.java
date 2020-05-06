package com.cloud.service.impl;

import com.alibaba.otter.canal.client.CanalConnector;
import com.alibaba.otter.canal.protocol.CanalEntry;
import com.alibaba.otter.canal.protocol.Message;
import com.cloud.common.core.utils.JSONUtils;
import com.cloud.config.NovelCanalConfig;
import com.cloud.mq.kafka.service.KafkaSender;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@Slf4j
public class NovelTask implements Runnable, ApplicationContextAware {

    private ApplicationContext applicationContext;

    @Autowired
    @Qualifier("novelConnector")
    CanalConnector connector;

    @Autowired
    NovelCanalConfig canalConfig;

    @Autowired
    KafkaSender kafkaSender;

    @Scheduled(fixedDelay = 100)
    @Override
    public void run() {
        Message message = null;
        connector.connect();
        connector.subscribe("novel.story");
        connector.rollback();
        try {
            while (true) {
                // 不指定 position 获取事件，该方法返回的条件: 尝试拿batchSize条记录，有多少取多少，不会阻塞等
                message = connector.getWithoutAck(this.canalConfig.getBatchSize());
                long batchId = message.getId();
                // 返回本次获取的事件数量
                int size = message.getEntries().size();
                // 如果没有获取到则休息1秒钟
                if (batchId == -1 || size == 0) {
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                } else {
                    // 如果获取到了则处理数据
                    List<String> results = parse(message);
                    // 将数据推送给MQ
                    for (String row :results) {
                        System.out.println("[NovelTask推送到Kafka]: " + row);
                        this.kafkaSender.send("novel", row);
                    }
                }
                // 提交确认
                connector.ack(batchId);
                // connector.rollback(batchId); // 处理失败, 回滚数据
            }
        } finally {
            this.connector.disconnect();
        }

    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }


    /**
     * 解析Canal 抓取的数据
     * @param message
     * @return
     */
    private List<String> parse(Message message) {
        List<CanalEntry.Entry> entries= message.getEntries();
        CanalEntry.RowChange rowChage = null;
        CanalEntry.EventType eventType = null;
        List<String> events = new ArrayList<>();
        for (CanalEntry.Entry entry : entries) {
            if (entry.getEntryType() == CanalEntry.EntryType.TRANSACTIONBEGIN || entry.getEntryType() == CanalEntry.EntryType.TRANSACTIONEND) {
                continue;
            }

            try {
                rowChage = CanalEntry.RowChange.parseFrom(entry.getStoreValue());
            } catch (Exception e) {
                throw new RuntimeException("解析binlog数据发生错误:" + entry.toString(), e);
            }
            // 获取事件类型
            eventType = rowChage.getEventType();
            Map<String,Object> rows = null;

            for (CanalEntry.RowData rowData : rowChage.getRowDatasList()) {
                rows = new HashMap<>();
                rows.put("type",eventType.name());
                Map<String,String> old = parseColumnList(rowData.getBeforeColumnsList());
                Map<String,String> data = parseColumnList(rowData.getAfterColumnsList());
                rows.put("old", old);
                rows.put("data", data);
                events.add(JSONUtils.objectToJson(rows));
            }
        }
        return events;
    }

    private Map<String,String> parseColumnList(List<CanalEntry.Column> columns) {
        Map<String,String> columnMap = new HashMap<>();
        for (CanalEntry.Column column : columns) {
            columnMap.put(column.getName(),column.getValue());
        }
        return columnMap;
    }
}
