package com.cloud.api;

import com.cloud.common.core.bean.ResponseResult;
import com.cloud.common.core.utils.BeanUtils;
import com.cloud.dto.pay.RefundRecordDTO;
import com.cloud.model.pay.RefundRecord;
import com.cloud.vo.pay.RefundRecordVO;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import java.time.LocalDateTime;
import java.util.List;

@FeignClient("cloud-mall-pay")
public interface RefundRecordService {
    @RequestMapping(value = "/catalog/trade/refund/add",method = RequestMethod.POST)
    public ResponseResult save(@RequestBody RefundRecordVO refundRecordVO);

    @RequestMapping(value = "/catalog/trade/refund/update",method = RequestMethod.PUT)
    public ResponseResult update(@RequestBody RefundRecordVO refundRecordVO);

    @RequestMapping(value = "/catalog/trade/refund/deleteById",method = RequestMethod.POST)
    public ResponseResult deleteById(@RequestParam("id") Long id);

    @RequestMapping(value = "/catalog/trade/refund/deleteByOrderId",method = RequestMethod.POST)
    public ResponseResult deleteByOrderId(@RequestParam("orderId") Long orderId);
    @RequestMapping(value = "/catalog/trade/refund/deleteByOrderNo",method = RequestMethod.POST)
    public ResponseResult deleteByOrderNo(@RequestParam("orderNo") Long orderNo);

    @RequestMapping(value = "/catalog/trade/refund/findByTradeNo",method = RequestMethod.GET)
    public ResponseResult<RefundRecordVO> findByTradeNo(@RequestParam("tradeNo") String tradeNo);

    @RequestMapping(value = "/catalog/trade/refund/findById",method = RequestMethod.GET)
    public ResponseResult<RefundRecordVO> findById(@RequestParam("id") Long id);

    @RequestMapping(value = "/catalog/trade/refund/findByOrderId",method = RequestMethod.GET)
    public ResponseResult<RefundRecordVO> findByOrderId(@RequestParam("orderId") Long orderId);

    @RequestMapping(value = "/catalog/trade/refund/findByOrderNo",method = RequestMethod.GET)
    public ResponseResult<RefundRecordVO> findByOrderNo(@RequestParam("orderNo") Long orderNo);

    @RequestMapping(value = "/catalog/trade/refund/findByBuyerId",method = RequestMethod.GET)
    public ResponseResult<List<RefundRecord>> findByBuyerId(@RequestParam("buyerId") Long buyerId);
}
