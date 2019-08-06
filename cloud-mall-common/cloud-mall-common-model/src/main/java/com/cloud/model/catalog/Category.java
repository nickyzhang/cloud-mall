package com.cloud.model.catalog;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class Category implements Serializable{

    private Long id;

    private String name;

    private String description;

    private String imagePath;

    private int displayOrder;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;

    private Category parent;

    private List<Brand> brandList;

    private List<Product> productList;

    private List<AttributeName> attributeNameList;

    public Category() {

    }

    public Category(Long id, String name, String description, String imagePath, int displayOrder, LocalDateTime createTime, LocalDateTime updateTime, Category parent, List<Brand> brandList, List<Product> productList, List<AttributeName> attributeNameList) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.imagePath = imagePath;
        this.displayOrder = displayOrder;
        this.createTime = createTime;
        this.updateTime = updateTime;
        this.parent = parent;
        this.brandList = brandList;
        this.productList = productList;
        this.attributeNameList = attributeNameList;
    }
}
