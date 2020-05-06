package com.cloud.vo.pay;

import com.cloud.BaseModel;
import lombok.Data;

@Data
public class PayNotifyLogVO extends BaseModel {

    private static final long serialVersionUID = -5104597928654040053L;

    private Long id;

    private Long notifyId;

    private Long orderId;

    private String request;

    private String response;

    private String httpStatus;
}
