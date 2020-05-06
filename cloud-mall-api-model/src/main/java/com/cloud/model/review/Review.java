package com.cloud.model.review;

import com.cloud.BaseModel;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class Review extends BaseModel {

    private static final long serialVersionUID = 555068170334149268L;

    private Long reviewId;

    private Long skuId;

    private String skuName;

    private String skuDesc;

    private String skuImageUrl;

    private BigDecimal listPrice;

    private BigDecimal salePrice;

    private Long userId;

    private String userName;

    private String location;

    private String email;

    private float score;

    private String headline;

    private String remark;

    private List<String> pictures;

    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonFormat(pattern = "/catalogyyyy-MM-dd HH:mm:ss")
    private LocalDateTime remarkTime;

    /** Casual，Trendy，Sporty，Fashion Forward，Classic，Formal*/
    private Integer style;

    /**Low,Average,High*/
    private Integer quality;

    /** Loose，True To Size，Tight */
    private Integer fit;

    private boolean recommendable;
}
