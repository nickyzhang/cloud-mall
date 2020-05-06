package com.cloud.dto.catalog;

import com.cloud.BaseModel;
import com.cloud.model.catalog.Bundle;
import com.cloud.model.catalog.Sku;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class BundleDto extends BaseModel {

    private static final long serialVersionUID = 2538086002477357175L;

    private Long id;

    private String name;

    private String description;

    private Integer quantity;


    private List<Sku> skuList;
}
