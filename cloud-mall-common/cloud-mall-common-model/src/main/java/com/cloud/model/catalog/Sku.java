package com.cloud.model.catalog;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class Sku implements Serializable  {

    private Long id;

    private String name;

    private String description;

    private String imageUrl;

    private Long costPrice;

    private Long listPrice;

    private Long salePrice;

    private String code;

    private String barCode;

    private boolean status;

    private int displayOrder;

    private Product product;

    private List<Bundle> bundleList;

    private List<AttributeValue> properties;

    private LocalDateTime startTime;

    private LocalDateTime endTime;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;

    public Sku() {
    }

    public Sku(Long id, String name, String description, String imageUrl, Long costPrice, Long listPrice, Long salePrice, String code, String barCode, boolean status, int displayOrder, Product product, List<Bundle> bundleList, List<AttributeValue> properties, LocalDateTime createTime, LocalDateTime updateTime) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.imageUrl = imageUrl;
        this.costPrice = costPrice;
        this.listPrice = listPrice;
        this.salePrice = salePrice;
        this.code = code;
        this.barCode = barCode;
        this.status = status;
        this.displayOrder = displayOrder;
        this.product = product;
        this.bundleList = bundleList;
        this.properties = properties;
        this.createTime = createTime;
        this.updateTime = updateTime;
    }
}
