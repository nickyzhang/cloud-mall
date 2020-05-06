package com.cloud.service.impl;

import com.cloud.common.core.utils.BeanUtils;
import com.cloud.dto.promotion.ActivityDTO;
import com.cloud.mapper.ActivityMapper;
import com.cloud.mapper.ActivitySkuMapper;
import com.cloud.mapper.PromotionRuleMapper;
import com.cloud.model.promotion.Activity;
import com.cloud.model.promotion.ActivitySku;
import com.cloud.model.promotion.PromotionRule;
import com.cloud.service.ActivityService;
import com.cloud.vo.promotion.ActivityVO;
import com.google.common.collect.Sets;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.*;

@Service
@Transactional
public class ActivityServiceImpl implements ActivityService {

    @Autowired
    ActivityMapper activityMapper;

    @Autowired
    ActivitySkuMapper activitySkuMapper;

    @Autowired
    PromotionRuleMapper promotionRuleMapper;

    @Override
    public int save(ActivityDTO activityDTO) {
        if (activityDTO == null) {
            return 0;
        }

        Activity activity = BeanUtils.copy(activityDTO,Activity.class);
        int code = this.activityMapper.save(activity);
        if (code == 0) {
            return 0;
        }
        List<Long> skuList = activityDTO.getSkuList();
        if (CollectionUtils.isNotEmpty(skuList)) {
            ActivitySku activitySku = null;
            for (Long skuId : skuList) {
                activitySku = new ActivitySku();
                activitySku.setSkuId(skuId);
                activitySku.setActivityId(activity.getActivityId());
                activitySku.setDeleted(false);
                activitySku.setCreateTime(LocalDateTime.now());
                activitySku.setUpdateTime(LocalDateTime.now());
                code = this.activitySkuMapper.saveActivitySku(activitySku);
                if (code == 0) {
                    return 0;
                }
            }
        }
        return code;
    }

    @Override
    public int saveList(List<Activity> activityList) {
        return this.activityMapper.saveList(activityList);
    }

    @Override
    public int update(ActivityDTO activityDTO) {
        if (activityDTO == null) {
            return 0;
        }

        Activity activity = BeanUtils.copy(activityDTO,Activity.class);
        int code = this.activityMapper.update(activity);
        if (code == 0) {
            return 0;
        }

        List<ActivitySku> activitySkuList = this.activitySkuMapper.findByActivityId(activity.getActivityId());
        if (CollectionUtils.isNotEmpty(activitySkuList)) {
            Set<Long> oldSkuSet = new HashSet<>();
            for (ActivitySku activitySku : activitySkuList) {
                oldSkuSet.add(activitySku.getActivityId());
            }

            Set<Long> newSkuSet = new HashSet<>(activityDTO.getSkuList());

            Set<Long> deleteSkuSet = Sets.difference(oldSkuSet,newSkuSet);
            for (Long deleteSkuId : deleteSkuSet) {
                Map<String,Object> map = new HashMap<>();
                map.put("activityId",activity.getActivityId());
                map.put("skuId",deleteSkuId);
                code = this.activitySkuMapper.deleteActivitySku(map);
                if (code == 0) {
                    return code;
                }
            }

            Set<Long> addSkuSet = Sets.difference(newSkuSet,oldSkuSet);
            for (Long addSkuId : addSkuSet) {
                ActivitySku activitySku = new ActivitySku();
                activitySku.setSkuId(addSkuId);
                activitySku.setActivityId(activity.getActivityId());
                activitySku.setDeleted(false);
                activitySku.setCreateTime(LocalDateTime.now());
                activitySku.setUpdateTime(LocalDateTime.now());
                code = this.activitySkuMapper.saveActivitySku(activitySku);
                if (code == 0) {
                    return 0;
                }
            }
        }
        return code;
    }

    @Override
    public int delete(Long activityId) {
        return this.activityMapper.delete(activityId);
    }

    @Override
    public ActivityVO find(Long activityId) {
        Activity activity = this.activityMapper.find(activityId);
        if (activity == null) {
            return null;
        }

        ActivityDTO activityDTO = BeanUtils.copy(activity,ActivityDTO.class);
        List<ActivitySku> activitySkuList = this.activitySkuMapper.findByActivityId(activityId);
        List<Long> skuList = new ArrayList<>(10);
        if (CollectionUtils.isNotEmpty(activitySkuList)) {
            activitySkuList.forEach(element -> {
                skuList.add(element.getSkuId());
            });
            activityDTO.setSkuList(skuList);
        }

        return BeanUtils.copy(activityDTO, ActivityVO.class);
    }

    @Override
    public List<Activity> findByActivityType(Integer activityType) {
        return this.activityMapper.findByActivityType(activityType);
    }

    @Override
    public List<Activity> findBySkuId(Long skuId) {
        return this.activityMapper.findBySkuId(skuId);
    }

    @Override
    public List<Activity> findActivityListByTimeRange(String startTime, String endTime) {
        Map<String, Object> map = new HashMap<>();
        map.put("startTime",startTime);
        map.put("endTime",endTime);
        return this.activityMapper.findActivityListByTimeRange(map);
    }

    @Override
    public List<Activity> findAll() {
        return this.activityMapper.findAll();
    }

    @Override
    public Long count() {
        return this.activityMapper.count();
    }

    @Override
    public List<Activity> findActsBySkuIds(Long[] skuIds) {
        if (ArrayUtils.isEmpty(skuIds)) {
            return null;
        }
        return this.activityMapper.findActsBySkuIds(skuIds);
    }
}
