package com.cloud.mapper;

import com.cloud.model.order.OrderRemark;
import org.apache.ibatis.annotations.Mapper;
import java.util.List;

@Mapper
public interface OrderRemarkMapper {
    
    public int save(OrderRemark remark);

    public int update(OrderRemark remark);

    public int delete(Long remarkId);

    public OrderRemark findOrderRemarkById(Long remarkId);

    public List<OrderRemark> findOrderRemarkByOrderId(Long orderId);

    public List<OrderRemark> findOrderRemarkByOrderNo(Long orderNo);
}
