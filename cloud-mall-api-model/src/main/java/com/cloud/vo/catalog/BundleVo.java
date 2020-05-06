package com.cloud.vo.catalog;

import com.cloud.BaseModel;
import com.cloud.model.catalog.Bundle;
import com.cloud.model.catalog.Sku;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class BundleVo extends BaseModel {

    private static final long serialVersionUID = -9104030402127421450L;

    private Long id;

    private String name;

    private String description;

    private Integer quantity;

    private List<Sku> skuList;
}
