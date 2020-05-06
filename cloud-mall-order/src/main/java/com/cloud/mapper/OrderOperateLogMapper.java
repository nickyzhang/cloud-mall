package com.cloud.mapper;

import com.cloud.model.order.OrderOperateLog;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface OrderOperateLogMapper {
    
    public int save(OrderOperateLog log);

    public int saveList(List<OrderOperateLog> logs);

    public int update(OrderOperateLog log);

    public int delete(Long logId);

    public OrderOperateLog findOrderLogById(Long logId);

    public List<OrderOperateLog> findOrderLogByOrderId(Long orderId);

    public List<OrderOperateLog> findOrderLogByOrderNo(Long orderNo);
}
