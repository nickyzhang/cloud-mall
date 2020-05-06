package com.cloud.mapper;

import com.cloud.model.order.OrderRefund;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface OrderRefundMapper {

    public int save(OrderRefund refund);

    public int update(OrderRefund refund);

    public int delete(Long refundId);

    public OrderRefund findAfterServicesById(Long refundId);

    public OrderRefund findOrderRefundByServiceId(Long serviceId);

    public List<OrderRefund> findOrderRefundByOrderId(Long orderId);

    public List<OrderRefund> findOrderRefundByOrderNo(Long orderNo);
}
