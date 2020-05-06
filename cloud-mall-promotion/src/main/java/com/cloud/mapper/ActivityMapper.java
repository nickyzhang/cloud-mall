package com.cloud.mapper;

import com.cloud.model.promotion.Activity;
import org.apache.ibatis.annotations.Mapper;
import java.util.List;
import java.util.Map;

@Mapper
public interface ActivityMapper {

    public Activity find(Long activityId);

    public List<Activity> findBySkuId(Long skuId);

    public List<Activity> findByActivityType(Integer activityType);

    public List<Activity> findActivityListByTimeRange(Map<String,Object> map);

    public List<Activity> findAll();

    public Long count();

    public int save(Activity activity);

    public int saveList(List<Activity> activityList);

    public int update(Activity activity);

    public int delete(Long activityId);

    public List<Activity> findActsBySkuIds(Long[] skuIds);
}
