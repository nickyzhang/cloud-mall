package com.cloud.vo.order;

import com.cloud.BaseModel;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class OrderRemarkVO extends BaseModel{

    private static final long serialVersionUID = 2243690473503744237L;

    private Long remarkId;

    private Long orderId;

    private Long orderNo;

    private Integer packageScore;

    private Integer deliveryScore;

    private Integer attitudeScore;

    private Integer goodsScore;

    private String remark;

    private List<String> pictureList;

    private LocalDateTime remarkTime;
}
