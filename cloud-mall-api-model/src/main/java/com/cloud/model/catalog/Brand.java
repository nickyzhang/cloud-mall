package com.cloud.model.catalog;

import com.cloud.BaseModel;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class Brand extends BaseModel {

    private Long id;

    private String chineseName;

    private String englishName;

    private String description;

    private String logo;

    private String origin;
}
