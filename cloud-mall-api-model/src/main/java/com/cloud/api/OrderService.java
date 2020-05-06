package com.cloud.api;

import com.cloud.common.core.bean.ResponseResult;
import com.cloud.dto.order.OrderDTO;
import com.cloud.po.order.OrderReq;
import com.cloud.vo.order.OrderVO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(value = "cloud-mall-order")
public interface OrderService {

    @RequestMapping(value = "/order/createOrder",method = RequestMethod.POST)
    public ResponseResult createOrder(@RequestBody OrderDTO orderDTO);

    @RequestMapping(value = "/order/findOrderById", method = RequestMethod.GET)
    public ResponseResult<OrderVO> findOrderById(@RequestParam("orderId") Long orderId);
}
