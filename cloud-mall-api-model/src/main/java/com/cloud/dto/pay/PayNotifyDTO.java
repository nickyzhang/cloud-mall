package com.cloud.dto.pay;

import com.cloud.BaseModel;
import lombok.Data;

@Data
public class PayNotifyDTO extends BaseModel {

    private static final long serialVersionUID = 804695745426704897L;

    private Long id;

    private Long orderId;

    private Integer notifyStatus;

    private Integer notifyType;

    private Integer notifyTimes;

    private Integer maxNotifyTimes;
}
