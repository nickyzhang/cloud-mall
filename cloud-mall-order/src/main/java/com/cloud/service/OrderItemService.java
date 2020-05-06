package com.cloud.service;

import com.cloud.common.core.bean.ResponseResult;
import com.cloud.dto.order.OrderItemDTO;
import com.cloud.model.order.OrderItem;
import com.cloud.vo.order.OrderItemVO;
import java.util.List;

public interface OrderItemService {

    public ResponseResult save(OrderItemDTO itemDTO);

    public ResponseResult saveList(List<OrderItem> itemList);

    public ResponseResult update(OrderItemDTO itemDTO);

    public ResponseResult delete(Long itemId);

    public ResponseResult<OrderItemVO> findOrderItemById(Long itemId);

    public ResponseResult<List<OrderItemVO>> findOrderItemByOrderId(Long orderId);

    public ResponseResult<List<OrderItemVO>> findOrderItemByOrderNo(Long orderNo);
}
