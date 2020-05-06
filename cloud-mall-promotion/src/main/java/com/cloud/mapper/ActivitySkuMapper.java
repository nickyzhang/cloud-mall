package com.cloud.mapper;

import com.cloud.model.promotion.ActivitySku;
import org.apache.ibatis.annotations.Mapper;
import java.util.List;
import java.util.Map;

@Mapper
public interface ActivitySkuMapper {

    public List<ActivitySku> findBySkuId(Long skuId);

    public List<ActivitySku> findByActivityId(Long activityId);

    public int saveActivitySku(ActivitySku activitySku);

    public int deleteActivitySku(Map<String,Object> map);

    public int deleteActivitySkuBySkuId(Map<String,Object> map);

    public int deleteActivitySkuByActivityId(Map<String,Object> map);
}
