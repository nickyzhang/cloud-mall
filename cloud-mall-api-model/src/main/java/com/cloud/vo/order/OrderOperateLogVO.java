package com.cloud.vo.order;

import com.cloud.BaseModel;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class OrderOperateLogVO extends BaseModel {

    private static final long serialVersionUID = -201925914474677542L;

    private Long id;

    private Long orderId;

    private Long orderNo;

    private Integer type;

    private Integer orderStatus;

    private String desc;

    private String operator;

    private LocalDateTime operateTime;
}
