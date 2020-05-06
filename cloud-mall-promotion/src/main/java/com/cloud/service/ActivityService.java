package com.cloud.service;

import com.cloud.dto.promotion.ActivityDTO;
import com.cloud.model.promotion.Activity;
import com.cloud.model.promotion.ActivitySku;
import com.cloud.model.promotion.PromotionRule;
import com.cloud.vo.promotion.ActivityVO;

import java.util.List;
import java.util.Map;

public interface ActivityService {

    public int save(ActivityDTO activityDTO);

    public int saveList(List<Activity> activityList);

    public int update(ActivityDTO activityDTO);

    public int delete(Long activityId);

    public ActivityVO find(Long activityId);

    public List<Activity> findByActivityType(Integer activityType);

    public List<Activity> findBySkuId(Long skuId);

    public List<Activity> findActivityListByTimeRange(String startTime, String endTime);

    public List<Activity> findAll();

    public Long count();

    public List<Activity> findActsBySkuIds(Long[] skuIds);
}
