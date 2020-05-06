package com.cloud.api;

import com.cloud.common.core.bean.ResponseResult;
import com.cloud.model.pay.PayRecord;
import com.cloud.po.pay.PayRecordParam;
import com.cloud.vo.pay.PayRecordVO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import java.util.List;

@FeignClient("cloud-mall-pay")
public interface PayService {

    @RequestMapping(value = "/catalog/trade/add",method = RequestMethod.POST)
    public ResponseResult save(@RequestBody PayRecordParam payRecordParam);

    @RequestMapping(value = "/catalog/trade/update",method = RequestMethod.PUT)
    public ResponseResult update(@RequestBody PayRecordVO payRecordVO);

    @RequestMapping(value = "/catalog/trade/deleteById",method = RequestMethod.POST)
    public ResponseResult deleteById(@RequestParam("tradeId") Long tradeId);

    @RequestMapping(value = "/catalog/trade/deleteByOrderId",method = RequestMethod.POST)
    public ResponseResult deleteByOrderId(@RequestParam("orderId") Long orderId);

    @RequestMapping(value = "/catalog/trade/deleteByOrderNo",method = RequestMethod.POST)
    public ResponseResult deleteByOrderNo(@RequestParam("orderNo") Long orderNo);

    @RequestMapping(value = "/catalog/trade/findByTradeNo",method = RequestMethod.GET)
    public ResponseResult<PayRecordVO> findByTradeNo(@RequestParam("tradeNo") String tradeNo);

    @RequestMapping(value = "/catalog/trade/findById",method = RequestMethod.GET)
    public ResponseResult<PayRecordVO> findById(@RequestParam("tradeId") Long tradeId);

    @RequestMapping(value = "/catalog/trade/findByOrderId",method = RequestMethod.GET)
    public ResponseResult<PayRecordVO> findByOrderId(@RequestParam("orderId") Long orderId);

    @RequestMapping(value = "/catalog/trade/findByOrderNo",method = RequestMethod.GET)
    public ResponseResult<PayRecordVO> findByOrderNo(@RequestParam("orderNo") Long orderNo);

    @RequestMapping(value = "/catalog/trade/findByBuyerId",method = RequestMethod.GET)
    public ResponseResult<List<PayRecord>> findByBuyerId(@RequestParam("buyerId") Long buyerId);
}
