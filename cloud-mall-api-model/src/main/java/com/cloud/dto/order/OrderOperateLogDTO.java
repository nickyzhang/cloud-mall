package com.cloud.dto.order;

import com.cloud.BaseModel;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class OrderOperateLogDTO extends BaseModel{

    private static final long serialVersionUID = -1716266340185978525L;

    private Long id;

    private Long orderId;

    private Long orderNo;

    private Integer type;

    private Integer orderStatus;

    private String desc;

    private String operator;

    private LocalDateTime operateTime;
}
