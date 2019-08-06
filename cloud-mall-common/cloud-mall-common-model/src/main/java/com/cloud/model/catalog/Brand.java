package com.cloud.model.catalog;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class Brand implements Serializable {

    private Long id;

    private String chineseName;

    private String englishName;

    private String description;

    private String logo;

    private String origin;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;

    private List<Category> categoryList;

    public Brand() {
    }

    public Brand(Long id, String chineseName, String englishName, String description, String logo, String origin, LocalDateTime createTime, LocalDateTime updateTime, List<Category> categoryList) {
        this.id = id;
        this.chineseName = chineseName;
        this.englishName = englishName;
        this.description = description;
        this.logo = logo;
        this.origin = origin;
        this.createTime = createTime;
        this.updateTime = updateTime;
        this.categoryList = categoryList;
    }
}
