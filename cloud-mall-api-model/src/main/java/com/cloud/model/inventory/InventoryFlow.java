package com.cloud.model.inventory;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
public class InventoryFlow implements Serializable {

    private Long id;

    private Long skuId;

    private Long orderId;

    private Integer stockNumBefore;

    private Integer stockNumAfter;

    private Integer stockNumDiff;

    private Integer availableNumBefore;

    private Integer availableNumAfter;

    private Integer availableNumDiff;

    private Integer orderNumBefore;

    private Integer orderNumAfter;

    private Integer orderNumDiff;

    private Integer lockNumBefore;

    private Integer lockNumAfter;

    private Integer lockNumDiff;

    private Integer actionType;

    private Integer stockFrom;

    private Boolean deleted;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;

}
