package com.cloud.dto.catalog;

import com.cloud.BaseModel;
import com.cloud.model.catalog.AttributeValue;
import com.cloud.model.catalog.Bundle;
import com.cloud.model.catalog.Product;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import lombok.Data;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class SkuDto extends BaseModel {

    private static final long serialVersionUID = -2581975311948239899L;

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

    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonFormat(pattern = "/catalogyyyy-MM-dd HH:mm:ss")
    private LocalDateTime startTime;

    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonFormat(pattern = "/catalogyyyy-MM-dd HH:mm:ss")
    private LocalDateTime endTime;

    private Product product;

    private List<Bundle> bundleList;

    private List<AttributeValue> properties;
}
