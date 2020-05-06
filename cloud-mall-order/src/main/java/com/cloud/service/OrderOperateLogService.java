package com.cloud.service;

import com.cloud.dto.order.OrderOperateLogDTO;
import com.cloud.model.order.OrderOperateLog;
import com.cloud.vo.order.OrderOperateLogVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

public interface OrderOperateLogService {
    
    public int save(OrderOperateLogDTO logDTO);

    public int saveList(List<OrderOperateLog> logs);

    public int update(OrderOperateLogDTO logDTO);

    public int delete(Long logId);

    public OrderOperateLogVO findOrderLogById(Long logId);

    public List<OrderOperateLogVO> findOrderLogByOrderId(Long orderId);

    public List<OrderOperateLogVO> findOrderLogByOrderNo(Long orderNo);
}
