package com.cloud.controller;

import com.cloud.api.*;
import com.cloud.common.core.bean.ResponseResult;
import com.cloud.common.core.enums.ResultCodeEnum;
import com.cloud.common.core.utils.BeanUtils;
import com.cloud.dto.order.OrderDTO;
import com.cloud.dto.order.OrderItemDTO;
import com.cloud.enums.OrderChannel;
import com.cloud.enums.OrderStatus;
import com.cloud.enums.OrderType;
import com.cloud.enums.ShippingMethod;
import com.cloud.exception.OrderBizException;
import com.cloud.po.order.OrderItemReq;
import com.cloud.po.order.OrderReq;
import com.cloud.service.OrderAggregationService;
import com.cloud.utils.OrderNoGenUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/order")
public class OrderController {

    @Autowired
    OrderAggregationService orderService;

    @RequestMapping("/submit")
    public ResponseResult sumbitOrder(@RequestBody OrderReq orderReq, HttpServletRequest request) {
        ResponseResult result = new ResponseResult();
        if (orderReq == null) {
            throw new OrderBizException(ResultCodeEnum.ORDER_NULL.getCode(),
                    ResultCodeEnum.ORDER_NULL.getMessage());
        }

        String userId = (String)request.getAttribute("userId");
        if (StringUtils.isBlank(userId)) {
            userId = String.valueOf(orderReq.getMemberId());
            if (StringUtils.isBlank(userId)) {
                throw new OrderBizException(ResultCodeEnum.ORDER_USER_NOT_LOGIN.getCode(),
                        ResultCodeEnum.ORDER_USER_NOT_LOGIN.getMessage());
            }
        }

        OrderDTO orderDTO = new OrderDTO();
        int channel = OrderChannel.getOrderChannel(orderReq.getSource()).getCode();
        int type = OrderType.getOrderType(orderReq.getType()).getCode();
        orderDTO.setOrderNo(OrderNoGenUtils.genOrderNo(channel,type,Long.valueOf(userId)));
        orderDTO.setUserId(Long.valueOf(userId));
        orderDTO.setChannel(channel);
        orderDTO.setOrderType(type);
        orderDTO.setStatus(OrderStatus.WAITING_PAYMENT.getCode());
        orderDTO.setAddressId(orderReq.getAddressId());
        int shippingMethod = ShippingMethod.getShippingMethod(orderReq.getShippingMethod()).getCode();
        orderDTO.setShippingMethod(shippingMethod);
        if (orderDTO.getShippingMethod() == 1 ) {
            orderDTO.setFreight(BigDecimal.valueOf(12));
        } else if (orderDTO.getShippingMethod() == 2) {
            orderDTO.setFreight(BigDecimal.valueOf(8));
        } else if (orderDTO.getShippingMethod() == 3) {
            orderDTO.setFreight(BigDecimal.valueOf(6));
        }
        orderDTO.setTotalAmount(orderReq.getTotalAmount());
        orderDTO.setActivityAmount(orderReq.getActivityAmount());
        orderDTO.setCouponAmount(orderReq.getCouponAmount());
        orderDTO.setCouponList(orderReq.getCouponList());

        List<OrderItemReq> itemReqList = orderReq.getItemList();
        List<OrderItemDTO> itemList = new ArrayList<>();
        OrderItemDTO itemDTO = null;
        for (OrderItemReq itemReq : itemReqList) {
            itemDTO = BeanUtils.copy(itemReq, OrderItemDTO.class);
            itemDTO.setActivityAmount(itemReq.getActivitySavedMoney());
            itemDTO.setActivityList(itemReq.getActivityList());
            itemList.add(itemDTO);
        }
        orderDTO.setItemList(itemList);
        BigDecimal paymentAmount = orderDTO.getTotalAmount()
                .subtract(orderDTO.getActivityAmount())
                .subtract(orderDTO.getCouponAmount())
                .subtract(orderDTO.getFreight());
        if (paymentAmount.compareTo(BigDecimal.ZERO) > 0) {
            orderDTO.setPaymentAmount(paymentAmount);
        }

        return this.orderService.createOrder(orderDTO);
    }
}
