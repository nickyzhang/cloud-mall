package com.cloud.model.catalog;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
public class AttributeValue implements Serializable {

    private Long id;

    private String value;

    private int displayOrder;

    private AttributeName name;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;

    public AttributeValue() {
    }

    public AttributeValue(Long id, String value, int displayOrder, AttributeName name, LocalDateTime createTime, LocalDateTime updateTime) {
        this.id = id;
        this.value = value;
        this.displayOrder = displayOrder;
        this.name = name;
        this.createTime = createTime;
        this.updateTime = updateTime;
    }
}
