package com.cloud.api;

import com.cloud.common.core.bean.ResponseResult;
import com.cloud.common.core.service.GenIdService;
import com.cloud.common.core.utils.BeanUtils;
import com.cloud.dto.promotion.CouponDTO;
import com.cloud.model.promotion.Coupon;
import com.cloud.model.promotion.CouponTemplate;
import com.cloud.model.promotion.CouponUseRecord;
import com.cloud.service.CouponService;
import com.cloud.utils.CouponNoGenUtils;
import com.cloud.vo.promotion.CouponTemplateVO;
import com.cloud.vo.promotion.CouponVO;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
public class CouponController {

    @Autowired
    CouponService couponService;

    @Autowired
    CouponTemplateService templateService;

    @Autowired
    GenIdService genIdService;

    @RequestMapping(value = "/coupon/add", method = RequestMethod.POST)
    @ApiOperation(value = "添加优惠券")
    public ResponseResult save(@RequestBody CouponVO couponVO) {
        ResponseResult result = new ResponseResult();
        if (couponVO == null) {
            return result.failed("要保存的优惠券为空");
        }
        CouponDTO couponDTO = BeanUtils.copy(couponVO, CouponDTO.class);
        couponDTO.setCouponId(this.genIdService.genId());
        couponDTO.setCouponNo(CouponNoGenUtils.genCouponCode(couponDTO.getUserId()));
        couponDTO.setDeleted(Boolean.FALSE);
        LocalDateTime now = LocalDateTime.now();
        // 设置领取时间
        couponDTO.setReceiveTime(now);
        // 确定优惠券的到期时间
        ResponseResult<CouponTemplateVO> findTemplateResult = this.templateService.findTemplateByCouponId(couponDTO.getCouponId());
        if (findTemplateResult.getCode() == 200 && findTemplateResult.getData() == null) {
            CouponTemplateVO templateVO = findTemplateResult.getData();
            // 获取优惠券自领取之日开始的有效天数
            int validDays = templateVO.getValidDays();
            LocalDateTime expiredTime = couponDTO.getReceiveTime().plusDays(validDays);
            // 但是不能超过模板规定的到期时间，模板时间到期，所有优惠券必须到期
            if (expiredTime.isAfter(templateVO.getExpireTime())) {
                expiredTime = templateVO.getExpireTime();
            }
            couponDTO.setExpiredTime(expiredTime);
        }
        couponDTO.setCreateTime(now);
        couponDTO.setUpdateTime(now);
        return this.couponService.save(couponDTO);
    }

    @RequestMapping(value = "/coupon/batchAdd", method = RequestMethod.POST)
    @ApiOperation(value = "添加优惠券")
    public ResponseResult save(@RequestBody List<Coupon> couponList) {
        ResponseResult result = new ResponseResult();
        if (CollectionUtils.isEmpty(couponList)) {
            return result.failed("要批量保存的的优惠券为空");
        }

        return this.couponService.saveList(couponList);
    }

    @RequestMapping(value = "/coupon/update", method = RequestMethod.PUT)
    @ApiOperation(value = "更新优惠券")
    public ResponseResult update(@RequestBody CouponVO couponVO) {
        ResponseResult result = new ResponseResult();
        if (couponVO == null) {
            return result.failed("要更新的优惠券为空");
        }
        CouponDTO couponDTO = BeanUtils.copy(couponVO, CouponDTO.class);
        return this.couponService.update(couponDTO);
    }

    @RequestMapping(value = "/coupon/delete/{couponId}", method = RequestMethod.POST)
    @ApiOperation(value = "删除优惠券")
    public ResponseResult delete(@PathVariable("couponId") Long couponId) {
        ResponseResult result = new ResponseResult();
        if (couponId == null) {
            return result.failed("要删除优惠券ID为空");
        }
        return this.couponService.delete(couponId);
    }

    @RequestMapping(value = "/coupon/deleteUnavailableCouponList", method = RequestMethod.POST)
    @ApiOperation(value = "删除无效优惠券")
    public ResponseResult deleteUnavailableCouponList(){
        ResponseResult result = new ResponseResult();
        return this.couponService.deleteUnavailableCouponList();
    }

    @RequestMapping(value = "/coupon/deleteUnavailableCouponListByUserId", method = RequestMethod.POST)
    @ApiOperation(value = "删除用户无效优惠券")
    public ResponseResult deleteUnavailableCouponListByUserId(@RequestParam("userId") Long userId){
        ResponseResult result = new ResponseResult();
        if (userId == null) {
            return result.failed("用户ID参数为空");
        }
        return this.couponService.deleteUnavailableCouponListByUserId(userId);
    }

    @RequestMapping(value = "/coupon/list/{couponId}", method = RequestMethod.GET)
    @ApiOperation(value = "根据ID查找优惠券")
    public ResponseResult<CouponVO> find(@PathVariable("couponId") Long couponId) {
        ResponseResult result = new ResponseResult();
        if (couponId == null) {
            return result.failed("要查询优惠券ID参数为空");
        }
        CouponVO couponVO = this.couponService.find(couponId);
        return couponVO == null ? result.success("该优惠券不存在", null) : result.success("查询成功", couponVO);
    }

    @RequestMapping(value = "/coupon/findAll", method = RequestMethod.GET)
    @ApiOperation(value = "查找所有优惠券")
    public ResponseResult<List<Coupon>> findAll() {
        ResponseResult result = new ResponseResult();
        List<Coupon> couponList = this.couponService.findAll();
        return CollectionUtils.isEmpty(couponList) ? result.success("没有数据返回", null) : result.success("查询成功", couponList);
    }

    @RequestMapping(value = "/coupon/findCouponListByUserId", method = RequestMethod.GET)
    @ApiOperation(value = "查找用户对应的所有优惠券")
    public ResponseResult<List<Coupon>> findCouponListByUserId(@RequestParam("userId") Long userId) {
        ResponseResult result = new ResponseResult();
        if (userId == null) {
            return result.failed("用户ID参数为空");
        }
        List<Coupon> couponList = this.couponService.findCouponListByUserId(userId);
        return CollectionUtils.isEmpty(couponList) ? result.success("没有数据返回", null) : result.success("查询成功", couponList);
    }

    @RequestMapping(value = "/coupon/findUnavailableCouponListByUserId", method = RequestMethod.GET)
    @ApiOperation(value = "查找用户对应的无效的优惠券")
    public ResponseResult<List<Coupon>> findUnavailableCouponListByUserId(@RequestParam("userId") Long userId) {
        ResponseResult result = new ResponseResult();
        if (userId == null) {
            return result.failed("用户ID参数为空");
        }
        List<Coupon> couponList = this.couponService.findUnavailableCouponListByUserId(userId);
        return CollectionUtils.isEmpty(couponList) ? result.success("没有数据返回", null) : result.success("查询成功", couponList);
    }


    @RequestMapping(value = "/coupon/isExpired", method = RequestMethod.GET)
    @ApiOperation(value = "检查优惠券是否到期")
    public ResponseResult isExpired(@RequestParam("couponId") Long couponId) {
        ResponseResult result = new ResponseResult();
        if (couponId == null) {
            return result.failed("优惠券ID参数为空");
        }
        boolean expired = this.couponService.isExpired(couponId);
        return expired ? result.success("已到期", expired) : result.success("还没有到期", expired);
    }

    @RequestMapping(value = "/coupon/preUseCoupon", method = RequestMethod.POST)
    @ApiOperation(value = "预扣减优惠券")
    public ResponseResult preUseCoupon(@RequestBody List<Coupon> couponList){
        ResponseResult result = new ResponseResult();
        if (CollectionUtils.isEmpty(couponList)) {
            return result.failed("预扣减优惠券列表为空");
        }
        return couponService.preUseCoupon(couponList);
    }

    @RequestMapping(value = "/coupon/useCoupon", method = RequestMethod.POST)
    @ApiOperation(value = "扣减优惠券")
    public ResponseResult useCoupon(@RequestBody List<Coupon> couponList){
        ResponseResult result = new ResponseResult();
        return result;
//        if (CollectionUtils.isEmpty(couponList)) {
//            return result.failed("准备扣减优惠券列表为空");
//        }
//
//        return this.couponService.batchDelete(couponList);
    }
}
