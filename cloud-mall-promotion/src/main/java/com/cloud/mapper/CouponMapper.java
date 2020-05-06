package com.cloud.mapper;

import com.cloud.model.promotion.Coupon;
import org.apache.ibatis.annotations.Mapper;
import java.util.List;
import java.util.Map;

@Mapper
public interface CouponMapper {

    public int save(Coupon coupon);

    public int saveList(List<Coupon> couponList);

    public int update(Coupon coupon);

    public int delete(Long couponId);

    public int batchDelete(List<Long> couponIds);

    public int deleteCouponByTemplateId(Long templateId);

    public int deleteUnavailableCouponList();

    public int deleteUnavailableCouponListByUserId(Long userId);

    public Coupon find(Long couponId);

    public List<Coupon> findAll();

    public List<Coupon> findCouponListByUserId(Long userId);

    public List<Coupon> findUnavailableCouponListByUserId(Long userId);

    public int getCouponNumByUserAndTemplate(Map<String,Object> map);

}
