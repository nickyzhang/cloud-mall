package com.cloud.model.inventory;

import lombok.Data;
import java.io.Serializable;
import java.time.LocalDateTime;

@Data
public class Inventory implements Serializable {

    private Long id;

    private Long skuId;

    private String stockName;

    private Integer totalStock;

    private Integer availableStock;

    private Integer frozenStock;

    private Integer stockThreshold;

    private Boolean deleted;

    private Long version;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;
}
