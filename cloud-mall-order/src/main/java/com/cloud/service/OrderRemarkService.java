package com.cloud.service;

import com.cloud.dto.order.OrderRemarkDTO;
import com.cloud.model.order.OrderRemark;
import com.cloud.vo.order.OrderRemarkVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

public interface OrderRemarkService {
    
    public int save(OrderRemarkDTO remarkDTO);

    public int update(OrderRemarkDTO remarkDTO);

    public int delete(Long remarkId);

    public OrderRemarkVO findOrderRemarkById(Long remarkId);

    public List<OrderRemarkVO> findOrderRemarkByOrderId(Long orderId);

    public List<OrderRemarkVO> findOrderRemarkByOrderNo(Long orderNo);
}
