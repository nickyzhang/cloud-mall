package com.cloud.model.inventory;

import java.io.Serializable;
import java.time.LocalDateTime;

public class InventoryReminder implements Serializable {

    private Long id;

    private Long skuId;

    private String receiver;

    private String email;

    private Integer sendStatus;

    private Boolean deleted;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;

}
