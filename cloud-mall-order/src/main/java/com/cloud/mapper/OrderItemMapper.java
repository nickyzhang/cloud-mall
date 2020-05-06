package com.cloud.mapper;

import com.cloud.model.order.OrderItem;
import org.apache.ibatis.annotations.Mapper;
import java.util.List;

@Mapper
public interface OrderItemMapper {

    public int save(OrderItem item);

    public int saveList(List<OrderItem> itemList);

    public int update(OrderItem item);

    public int delete(Long itemId);

    public OrderItem findOrderItemById(Long itemId);

    public List<OrderItem> findOrderItemByOrderId(Long orderId);

    public List<OrderItem> findOrderItemByOrderNo(Long orderNo);
}
