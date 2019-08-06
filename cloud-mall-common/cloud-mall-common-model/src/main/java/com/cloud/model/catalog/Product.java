package com.cloud.model.catalog;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class Product implements Serializable {

    private Long id;

    private String name;

    private String description;

    private String imageUrl;

    private float rating;

    private int review;

    private int displayOrder;

    private Brand brand;

    private Category category;

    private List<Sku> skuList;

    private List<AttributeValue> properties;

    private LocalDateTime startTime;

    private LocalDateTime endTime;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;

    public Product() {
    }

    public Product(Long id, String name, String description, String imageUrl, float rating, int review, int displayOrder, Brand brand, Category category, List<Sku> skuList, List<AttributeValue> properties, LocalDateTime startTime, LocalDateTime endTime, LocalDateTime createTime, LocalDateTime updateTime) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.imageUrl = imageUrl;
        this.rating = rating;
        this.review = review;
        this.displayOrder = displayOrder;
        this.brand = brand;
        this.category = category;
        this.skuList = skuList;
        this.properties = properties;
        this.startTime = startTime;
        this.endTime = endTime;
        this.createTime = createTime;
        this.updateTime = updateTime;
    }
}
