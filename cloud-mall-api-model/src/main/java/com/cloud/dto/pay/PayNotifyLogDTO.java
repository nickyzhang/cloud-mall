package com.cloud.dto.pay;

import com.cloud.BaseModel;
import lombok.Data;

@Data
public class PayNotifyLogDTO extends BaseModel{

    private static final long serialVersionUID = -1973028979146360436L;

    private Long id;

    private Long notifyId;

    private Long orderId;

    private String request;

    private String response;

    private String httpStatus;
}
