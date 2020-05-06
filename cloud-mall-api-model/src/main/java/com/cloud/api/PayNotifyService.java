package com.cloud.api;

import com.cloud.common.core.bean.ResponseResult;
import com.cloud.model.pay.PayNotify;
import com.cloud.vo.pay.PayNotifyVO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import java.util.List;

@FeignClient("cloud-mall-pay")
public interface PayNotifyService {

    @RequestMapping(value = "/trade/notify/add", method = RequestMethod.POST)
    public ResponseResult save(@RequestBody PayNotifyVO payNotifyVO);
    @RequestMapping(value = "/trade/notify/update", method = RequestMethod.POST)
    public ResponseResult update(@RequestBody  PayNotifyVO payNotifyVO);

    @RequestMapping(value = "/trade/notify/deleteById", method = RequestMethod.POST)
    public ResponseResult deleteById(@RequestParam("id") Long id);

    @RequestMapping(value = "/trade/notify/deleteByOrderId", method = RequestMethod.POST)
    public ResponseResult deleteByOrderId(@RequestParam("orderId") Long orderId);

    @RequestMapping(value = "/trade/notify/findById", method = RequestMethod.GET)
    public ResponseResult<PayNotifyVO> findById(@RequestParam("id") Long id);

    @RequestMapping(value = "/trade/notify/findByOrderId", method = RequestMethod.GET)
    public ResponseResult<List<PayNotify>> findByOrderId(@RequestParam("orderId") Long orderId);
}
