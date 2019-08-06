package com.cloud.model.pay;

import com.cloud.model.AbstractModel;
import lombok.Getter;
import lombok.Setter;
import java.io.Serializable;

@Getter
@Setter
public class PayNotify extends AbstractModel implements Serializable {

    private static final long serialVersionUID = 9191719432399653184L;

    private Long id;

    private Long orderId;

    private Integer notifyStatus;

    private Integer notifyType;

    private Integer notifyTimes;

    private Integer maxNotifyTimes;
}
