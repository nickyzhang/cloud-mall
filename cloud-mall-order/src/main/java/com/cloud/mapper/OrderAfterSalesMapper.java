package com.cloud.mapper;

import com.cloud.model.order.OrderAfterSales;
import org.apache.ibatis.annotations.Mapper;
import java.util.List;

@Mapper
public interface OrderAfterSalesMapper {

    public int save(OrderAfterSales service);

    public int update(OrderAfterSales service);

    public int delete(Long serviceId);

    public OrderAfterSales findAfterServicesById(Long serviceId);

    public List<OrderAfterSales> findAfterServicesByOrderId(Long orderId);

    public List<OrderAfterSales> findAfterServicesByOrderNo(Long orderNo);
}
