package com.cloud.model.order;

import com.cloud.BaseModel;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class OrderOperateLog extends BaseModel {

    private static final long serialVersionUID = -3632641569208875007L;

    private Long id;

    private Long orderId;

    private Long orderNo;

    private Integer type;

    private Integer orderStatus;

    private String desc;

    private String operator;

    private LocalDateTime operateTime;
}
