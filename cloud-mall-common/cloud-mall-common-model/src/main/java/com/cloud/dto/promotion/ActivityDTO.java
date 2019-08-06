package com.cloud.dto.promotion;

import com.cloud.model.promotion.Activity;
import com.cloud.model.promotion.ActivitySku;
import com.cloud.model.promotion.PromotionRule;
import lombok.Data;
import java.util.List;

@Data
public class ActivityDTO extends Activity {

    private static final long serialVersionUID = -905971116310682503L;

    /** 促销规则列表 */
    private List<PromotionRule> ruleList;

    /** 促销活动对应的SKU列表 */
    private List<ActivitySku> activitySkuList;
}
