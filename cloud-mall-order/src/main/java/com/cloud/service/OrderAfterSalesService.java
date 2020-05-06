package com.cloud.service;

import com.cloud.dto.order.OrderAfterSalesDTO;
import com.cloud.model.order.OrderAfterSales;
import com.cloud.vo.order.OrderAfterSalesVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

public interface OrderAfterSalesService {

    public int save(OrderAfterSalesDTO serviceDTO);

    public int update(OrderAfterSalesDTO serviceDTO);

    public int delete(Long serviceId);

    public OrderAfterSalesVO findAfterServicesById(Long serviceId);

    public List<OrderAfterSalesVO> findAfterServicesByOrderId(Long orderId);

    public List<OrderAfterSalesVO> findAfterServicesByOrderNo(Long orderNo);
}
