package com.cloud.service;

import com.cloud.common.core.bean.ResponseResult;
import com.cloud.dto.order.OrderDTO;
import com.cloud.model.order.Order;
import com.cloud.vo.order.OrderVO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

public interface OrderService {

    public ResponseResult save(OrderDTO orderDTO);

    public ResponseResult saveList(List<Order> orderList);

    public ResponseResult update(OrderDTO orderDTO);

    public ResponseResult delete(Long orderId);

    public ResponseResult<OrderVO> findOrderById(Long orderId);

    public ResponseResult<OrderVO> findOrderByNo(Long orderNo);

    public ResponseResult<List<OrderVO>> findOrderListByUserId(Long userId);

    public ResponseResult<List<OrderVO>> findUserOrderListByTime(Long userId, String startTime, String endTime);

    public ResponseResult<List<OrderVO>> findOrderListByTime(String startTime, String endTime);

//    public int saveList(Long start, Long end, int orderNum);
}
