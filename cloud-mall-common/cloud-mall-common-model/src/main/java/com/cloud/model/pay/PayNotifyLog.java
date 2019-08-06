package com.cloud.model.pay;

import com.cloud.model.AbstractModel;
import lombok.Getter;
import lombok.Setter;
import java.io.Serializable;

@Getter
@Setter
public class PayNotifyLog extends AbstractModel implements Serializable {

    private static final long serialVersionUID = 8960949406300561060L;

    private Long id;

    private Long notifyId;

    private Long orderId;

    private String request;

    private String response;

    private String httpStatus;
}
