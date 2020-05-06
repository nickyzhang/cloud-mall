package com.cloud.model.catalog;

import com.cloud.BaseModel;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class Bundle extends BaseModel {

    private Long id;

    private String name;

    private String description;

    private Integer quantity;
}
