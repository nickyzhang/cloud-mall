package com.cloud.service;

import com.cloud.common.core.bean.ResponseResult;
import com.cloud.dto.order.OrderDTO;

public interface OrderAggregationService {

    public ResponseResult createOrder(OrderDTO orderDTO);
}
