package com.cloud.api;

import com.cloud.common.core.bean.ResponseResult;
import com.cloud.model.promotion.Coupon;
import com.cloud.vo.promotion.CouponVO;
import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@FeignClient(name="cloud-mall-promotion")
public interface CouponService {

    @RequestMapping(value = "/coupon/add", method = RequestMethod.POST)
    public ResponseResult save(@RequestBody CouponVO couponVO);

    @RequestMapping(value = "/coupon/batchAdd", method = RequestMethod.POST)
    public ResponseResult batchAdd(@RequestBody List<Coupon> couponList);

    @RequestMapping(value = "/coupon/preUseCoupon", method = RequestMethod.POST)
    public ResponseResult preUseCoupon(@RequestBody List<Coupon> couponList);

    @RequestMapping(value = "/coupon/update", method = RequestMethod.PUT)
    public ResponseResult update(@RequestBody CouponVO couponVO);

    @RequestMapping(value = "/coupon/delete/{couponId}", method = RequestMethod.POST)
    public ResponseResult delete(@PathVariable("couponId") Long couponId);

    @RequestMapping(value = "/coupon/deleteUnavailableCouponList", method = RequestMethod.POST)
    public ResponseResult deleteUnavailableCouponList();

    @RequestMapping(value = "/coupon/deleteUnavailableCouponListByUserId", method = RequestMethod.POST)
    public ResponseResult deleteUnavailableCouponListByUserId(@RequestParam("userId") Long userId);

    @RequestMapping(value = "/coupon/list/{couponId}", method = RequestMethod.GET)
    public ResponseResult<CouponVO> find(@PathVariable("couponId") Long couponId);

    @RequestMapping(value = "/coupon/findAll", method = RequestMethod.GET)
    public ResponseResult<List<Coupon>> findAll();

    @RequestMapping(value = "/coupon/findCouponListByUserId", method = RequestMethod.GET)
    public ResponseResult<List<Coupon>> findCouponListByUserId(@RequestParam("userId") Long userId);

    @RequestMapping(value = "/coupon/findUnavailableCouponListByUserId", method = RequestMethod.GET)
    public ResponseResult<List<Coupon>> findUnavailableCouponListByUserId(@RequestParam("userId") Long userId);

    @RequestMapping(value = "/coupon/findCouponListByCatId", method = RequestMethod.GET)
    public ResponseResult<List<Coupon>> findCouponListByCatId(@RequestParam("catId") Long catId);

    @RequestMapping(value = "/coupon/findCouponListByBrandId", method = RequestMethod.GET)
    public ResponseResult<List<Coupon>> findCouponListByBrandId(@RequestParam("brandId") Long brandId);
}
