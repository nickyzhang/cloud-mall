package com.cloud.model.inventory;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
public class InventoryUploadLog implements Serializable {

    private Long id;

    private Integer uploadType;

    private Integer uploadStatus;

    private LocalDateTime startTime;

    private LocalDateTime endTime;

    private Boolean deleted;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;
}
