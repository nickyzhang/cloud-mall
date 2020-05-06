package com.cloud.service;

import com.cloud.common.core.bean.ResponseResult;
import com.cloud.dto.promotion.CouponDTO;
import com.cloud.model.order.Order;
import com.cloud.model.promotion.Coupon;
import com.cloud.vo.promotion.CouponVO;

import java.util.List;

public interface CouponService {

    public ResponseResult save(CouponDTO couponDTO);

    public ResponseResult saveList(List<Coupon> couponList);

    public ResponseResult update(CouponDTO couponDTO);

    public ResponseResult delete(Long couponId);

    public ResponseResult batchDelete(List<Coupon> couponList);

    public ResponseResult deleteCouponByTemplateId(Long templateId);

    public ResponseResult deleteUnavailableCouponList();

    public ResponseResult deleteUnavailableCouponListByUserId(Long userId);

    public boolean isExpired(Long couponId);

    public CouponVO find(Long couponId);

    public List<Coupon> findAll();

    public List<Coupon> findCouponListByUserId(Long userId);

    public List<Coupon> findUnavailableCouponListByUserId(Long userId);

    public ResponseResult useCoupon(List<Coupon> couponList, Order order);

    public ResponseResult preUseCoupon(List<Coupon> couponList);
}
