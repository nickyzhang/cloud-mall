package com.cloud.model.catalog;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class Bundle implements Serializable {

    private Long id;

    private String name;

    private String description;

    private Integer quantity;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;

    private List<Sku> skuList;

    public Bundle() {
    }

    public Bundle(Long id, String name, String description, Integer quantity, LocalDateTime createTime, LocalDateTime updateTime, List<Sku> skuList) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.quantity = quantity;
        this.createTime = createTime;
        this.updateTime = updateTime;
        this.skuList = skuList;
    }
}
