package com.cloud.vo.pay;

import com.cloud.BaseModel;
import lombok.Data;

@Data
public class PayNotifyVO extends BaseModel {

    private static final long serialVersionUID = -457410734113886937L;

    private Long id;

    private Long orderId;

    private Integer notifyStatus;

    private Integer notifyType;

    private Integer notifyTimes;

    private Integer maxNotifyTimes;
}
