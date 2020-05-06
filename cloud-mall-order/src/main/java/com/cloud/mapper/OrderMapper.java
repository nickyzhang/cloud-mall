package com.cloud.mapper;

import com.cloud.model.order.Order;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface OrderMapper {

    public int save(Order order);

    public int saveList(List<Order> orderList);

    public int update(Order order);

    public int delete(Long orderId);

    public Order findOrderById(Long orderId);

    public Order findOrderByNo(Long orderNo);

    public List<Order> findOrderListByUserId(Long userId);

    public List<Order> findUserOrderListByTime(Map<String,Object> map);

    public List<Order> findOrderListByTime(Map<String,Object> map);
}
