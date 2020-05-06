package com.cloud.api;

import com.cloud.common.core.bean.ResponseResult;
import com.cloud.model.promotion.CouponUseRecord;
import com.cloud.vo.promotion.CouponUseRecordVO;
import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name="cloud-mall-promotion")
public interface CouponUseRecordService {

    @RequestMapping(value = "/couponUseRecord/add",method = RequestMethod.POST)
    public ResponseResult save(CouponUseRecordVO couponUseRecordVO);

    @RequestMapping(value = "/couponUseRecord/batchAdd",method = RequestMethod.POST)
    public ResponseResult batchSave(@RequestBody List<CouponUseRecordVO> couponUseRecordList);

    @RequestMapping(value = "/couponUseRecord/update",method = RequestMethod.PUT)
    public ResponseResult update(CouponUseRecordVO couponUseRecordVO);

    @RequestMapping(value = "/couponUseRecord/delete/{id}",method = RequestMethod.POST)
    public ResponseResult delete(@PathVariable("id") Long id);

    @RequestMapping(value = "/couponUseRecord/deleteUseRecordByUserId",method = RequestMethod.POST)
    public ResponseResult deleteUseRecordByUserId(@RequestParam("userId") Long userId);

    @RequestMapping(value = "/couponUseRecord/deleteUseRecordByCouponId",method = RequestMethod.POST)
    public ResponseResult deleteUseRecordByCouponId(@RequestParam("couponId") Long couponId);

    @RequestMapping(value = "/couponUseRecord/list/{id}",method = RequestMethod.GET)
    public ResponseResult<CouponUseRecordVO> find(@PathVariable("id") Long id);

    @RequestMapping(value = "/couponUseRecord/findUseRecordByCouponNo",method = RequestMethod.GET)
    public ResponseResult<CouponUseRecordVO> findUseRecordByCouponNo(@RequestParam("couponNo") String couponNo);

    @RequestMapping(value = "/couponUseRecord/findUseRecordByCouponId",method = RequestMethod.GET)
    public ResponseResult<CouponUseRecordVO> findUseRecordByCouponId(@RequestParam("couponId") Long couponId);

    @RequestMapping(value = "/couponUseRecord/findUseRecordListByUserId",method = RequestMethod.GET)
    public ResponseResult<List<CouponUseRecord>> findUseRecordListByUserId(@RequestParam("userId") Long userId);

    @RequestMapping(value = "/couponUseRecord/findUseRecordListByOrderId",method = RequestMethod.GET)
    public ResponseResult<List<CouponUseRecord>> findUseRecordListByOrderId(@RequestParam("orderId") Long orderId);

    @RequestMapping(value = "/couponUseRecord/findUseRecordByOrderNo",method = RequestMethod.GET)
    public ResponseResult<List<CouponUseRecord>> findUseRecordByOrderNo(@RequestParam("orderNo") String orderNo);

    @RequestMapping(value = "/couponUseRecord/findUseRecordByCouponNo",method = RequestMethod.GET)
    public ResponseResult<List<CouponUseRecord>> findAll();

    @RequestMapping(value = "/couponUseRecord/getUseNumByTemplateId",method = RequestMethod.GET)
    public ResponseResult getUseNumByTemplateId(@RequestParam("templateId") Long templateId);
}
