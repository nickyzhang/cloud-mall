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

    @RequestMapping(value = "/catalog/couponUseRecord/add",method = RequestMethod.POST)
    public ResponseResult save(CouponUseRecordVO couponUseRecordVO);

    @RequestMapping(value = "/catalog/couponUseRecord/batchAdd",method = RequestMethod.POST)
    public ResponseResult batchSave(@RequestBody List<CouponUseRecordVO> couponUseRecordList);

    @RequestMapping(value = "/catalog/couponUseRecord/update",method = RequestMethod.PUT)
    public ResponseResult update(CouponUseRecordVO couponUseRecordVO);

    @RequestMapping(value = "/catalog/couponUseRecord/delete/{id}",method = RequestMethod.POST)
    public ResponseResult delete(@PathVariable("id") Long id);

    @RequestMapping(value = "/catalog/couponUseRecord/deleteUseRecordByUserId",method = RequestMethod.POST)
    public ResponseResult deleteUseRecordByUserId(@RequestParam("userId") Long userId);

    @RequestMapping(value = "/catalog/couponUseRecord/deleteUseRecordByCouponId",method = RequestMethod.POST)
    public ResponseResult deleteUseRecordByCouponId(@RequestParam("couponId") Long couponId);

    @RequestMapping(value = "/catalog/couponUseRecord/list/{id}",method = RequestMethod.GET)
    public ResponseResult<CouponUseRecordVO> find(@PathVariable("id") Long id);

    @RequestMapping(value = "/catalog/couponUseRecord/findUseRecordByCouponNo",method = RequestMethod.GET)
    public ResponseResult<CouponUseRecordVO> findUseRecordByCouponNo(@RequestParam("couponNo") String couponNo);

    @RequestMapping(value = "/catalog/couponUseRecord/findUseRecordByCouponId",method = RequestMethod.GET)
    public ResponseResult<CouponUseRecordVO> findUseRecordByCouponId(@RequestParam("couponId") Long couponId);

    @RequestMapping(value = "/catalog/couponUseRecord/findUseRecordListByUserId",method = RequestMethod.GET)
    public ResponseResult<List<CouponUseRecord>> findUseRecordListByUserId(@RequestParam("userId") Long userId);

    @RequestMapping(value = "/catalog/couponUseRecord/findUseRecordListByOrderId",method = RequestMethod.GET)
    public ResponseResult<List<CouponUseRecord>> findUseRecordListByOrderId(@RequestParam("orderId") Long orderId);

    @RequestMapping(value = "/catalog/couponUseRecord/findUseRecordByOrderNo",method = RequestMethod.GET)
    public ResponseResult<List<CouponUseRecord>> findUseRecordByOrderNo(@RequestParam("orderNo") String orderNo);

    @RequestMapping(value = "/catalog/couponUseRecord/findUseRecordByCouponNo",method = RequestMethod.GET)
    public ResponseResult<List<CouponUseRecord>> findAll();

    @RequestMapping(value = "/catalog/couponUseRecord/getUseNumByTemplateId",method = RequestMethod.GET)
    public ResponseResult getUseNumByTemplateId(@RequestParam("templateId") Long templateId);
}
