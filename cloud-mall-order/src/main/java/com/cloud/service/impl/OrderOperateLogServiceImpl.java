package com.cloud.service.impl;

import com.cloud.common.core.utils.BeanUtils;
import com.cloud.dto.order.OrderOperateLogDTO;
import com.cloud.mapper.OrderOperateLogMapper;
import com.cloud.model.order.OrderOperateLog;
import com.cloud.service.OrderOperateLogService;
import com.cloud.vo.order.OrderOperateLogVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderOperateLogServiceImpl implements OrderOperateLogService {
    @Autowired
    OrderOperateLogMapper operateLogMapper;

    @Override
    public int save(OrderOperateLogDTO logDTO) {
        OrderOperateLog log = BeanUtils.copy(logDTO,OrderOperateLog.class);
        return operateLogMapper.save(log);
    }

    @Override
    public int saveList(List<OrderOperateLog> logs) {
        return operateLogMapper.saveList(logs);
    }

    @Override
    public int update(OrderOperateLogDTO logDTO) {
        return 0;
    }

    @Override
    public int delete(Long logId) {
        return 0;
    }

    @Override
    public OrderOperateLogVO findOrderLogById(Long logId) {
        return null;
    }

    @Override
    public List<OrderOperateLogVO> findOrderLogByOrderId(Long orderId) {
        return null;
    }

    @Override
    public List<OrderOperateLogVO> findOrderLogByOrderNo(Long orderNo) {
        return null;
    }
}
