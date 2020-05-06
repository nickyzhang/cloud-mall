package com.cloud.model.catalog;

import com.cloud.BaseModel;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class Category extends BaseModel {

    private Long id;

    private Long parentId;

    private String name;

    private String description;

    private String imagePath;

    private int displayOrder;
}
