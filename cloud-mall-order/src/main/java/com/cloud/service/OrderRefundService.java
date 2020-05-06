package com.cloud.service;

import com.cloud.dto.order.OrderRefundDTO;
import com.cloud.model.order.OrderRefund;
import com.cloud.vo.order.OrderRefundVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

public interface OrderRefundService {

    public int save(OrderRefundDTO refundDTO);

    public int update(OrderRefundDTO refundDTO);

    public int delete(Long refundId);

    public OrderRefundVO findAfterServicesById(Long refundId);

    public OrderRefundVO findOrderRefundByServiceId(Long serviceId);

    public List<OrderRefundVO> findOrderRefundByOrderId(Long orderId);

    public List<OrderRefundVO> findOrderRefundByOrderNo(Long orderNo);
}
