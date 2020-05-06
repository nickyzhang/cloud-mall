package com.cloud.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.cloud.common.core.bean.ResponseResult;
import com.cloud.common.core.service.GenIdService;
import com.cloud.common.core.utils.BeanUtils;
import com.cloud.dto.order.OrderDTO;
import com.cloud.dto.order.OrderItemDTO;
import com.cloud.dto.order.OrderOperateLogDTO;
import com.cloud.mapper.OrderItemMapper;
import com.cloud.mapper.OrderMapper;
import com.cloud.mapper.OrderOperateLogMapper;
import com.cloud.model.order.Order;
import com.cloud.model.order.OrderItem;
import com.cloud.model.order.OrderOperateLog;
import com.cloud.service.OrderOperateLogService;
import com.cloud.service.OrderService;
import com.cloud.utils.OrderNoGenUtils;
import com.cloud.vo.order.OrderVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.*;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    OrderMapper orderMapper;

    @Autowired
    OrderItemMapper itemMapper;

    @Autowired
    GenIdService genIdService;

    @Autowired
    OrderOperateLogMapper operateLogMapper;

    // 自动取消超时的订单
    // 取消订单
    public ResponseResult cancelOrder(Long orderId) {
        return null;
    }

    @Override
    public ResponseResult save(OrderDTO orderDTO) {
        ResponseResult result = new ResponseResult();
        if (orderDTO == null) {
            return result.failed("订单为空");
        }

        Order order = BeanUtils.copy(orderDTO,Order.class);
        order.setOrderId(this.genIdService.genId());
        order.setOrderNo(OrderNoGenUtils.genOrderNo(order.getChannel(),order.getOrderType(),order.getUserId()));
        LocalDateTime now = LocalDateTime.now();
        order.setCreateTime(now);
        order.setUpdateTime(now);
        int code = this.orderMapper.save(order);
        if (code == 0) {
            return result.failed("订单保存失败");
        }

        List<OrderItemDTO> itemDTOList = orderDTO.getItemList();
        List<OrderItem> itemList = BeanUtils.copy(itemDTOList,OrderItem.class);

        itemList.forEach( item -> {
            item.setItemId(this.genIdService.genId());
            item.setCreateTime(now);
            item.setUpdateTime(now);
        });

        code = itemMapper.saveList(itemList);
        if (code == 0) {
            return result.failed("订单保存失败");
        }

        OrderOperateLogDTO orderOperateLogDTO = new OrderOperateLogDTO();
        orderOperateLogDTO.setId(this.genIdService.genId());
        orderOperateLogDTO.setOrderId(orderDTO.getOrderId());
        orderOperateLogDTO.setOrderNo(orderDTO.getOrderNo());
        orderOperateLogDTO.setOrderStatus(orderDTO.getStatus());
        orderOperateLogDTO.setType(1);
        // orderOperateLogDTO.setDesc(JSONObject.toJSONString(orderDTO));
        orderOperateLogDTO.setDesc("");
        orderOperateLogDTO.setOperator("顾客");
        orderOperateLogDTO.setOperateTime(now);
        orderOperateLogDTO.setCreateTime(now);
        orderOperateLogDTO.setUpdateTime(now);
        OrderOperateLog orderOperateLog = BeanUtils.copy(orderOperateLogDTO,OrderOperateLog.class);
        code = this.operateLogMapper.save(orderOperateLog);

        return code == 0 ? result.failed("订单日志保存失败") : result.success("成功",null);
    }

    @Override
    public ResponseResult saveList(List<Order> orderList) {
        ResponseResult result = new ResponseResult();
        int code = this.orderMapper.saveList(orderList);
        return code == 0 ? result.failed("保存订单项失败") :
                result.success("成功",null);
    }

    @Override
    public ResponseResult update(OrderDTO orderDTO) {
        ResponseResult result = new ResponseResult();
        if (orderDTO == null) {
            return result.failed("订单为空");
        }
        Order order = BeanUtils.copy(orderDTO,Order.class);
        order.setUpdateTime(LocalDateTime.now());
        int code = this.orderMapper.update(order);
        return code == 0 ? result.failed("更新订单失败") : result.success("成功",null);
    }

    @Override
    public ResponseResult delete(Long orderId) {
        ResponseResult result = new ResponseResult();
        int code = this.orderMapper.delete(orderId);
        return code == 0 ? result.failed("删除订单失败") : result.success("成功",null);
    }

    @Override
    public ResponseResult<OrderVO> findOrderById(Long orderId) {
        ResponseResult<OrderVO> result = new ResponseResult<OrderVO>();
        Order order = this.orderMapper.findOrderById(orderId);
        return result.success("成功",BeanUtils.copy(order,OrderVO.class));
    }

    @Override
    public ResponseResult<OrderVO> findOrderByNo(Long orderNo) {
        ResponseResult<OrderVO> result = new ResponseResult<OrderVO>();
        Order order = this.orderMapper.findOrderByNo(orderNo);
        return result.success("成功",BeanUtils.copy(order,OrderVO.class));
    }

    // 应该分页查询, 我的订单
    @Override
    public ResponseResult<List<OrderVO>> findOrderListByUserId(Long userId) {
        ResponseResult<List<OrderVO>> result = new ResponseResult<>();
        List<Order> orderList = this.orderMapper.findOrderListByUserId(userId);
        return result.success("成功",BeanUtils.copy(orderList,OrderVO.class));
    }

    @Override
    public ResponseResult<List<OrderVO>> findUserOrderListByTime(Long userId, String startTime, String endTime) {
        ResponseResult<List<OrderVO>> result = new ResponseResult<>();
        Map<String,Object> param = new HashMap<>();
        param.put("userId",userId);
        param.put("startTime",startTime);
        param.put("endTime",endTime);
        List<Order> orderList = this.orderMapper.findUserOrderListByTime(param);
        return result.success("成功",BeanUtils.copy(orderList,OrderVO.class));
    }

    @Override
    public ResponseResult<List<OrderVO>> findOrderListByTime(String startTime, String endTime) {
        ResponseResult<List<OrderVO>> result = new ResponseResult<>();
        Map<String,Object> param = new HashMap<>();
        param.put("startTime",startTime);
        param.put("endTime",endTime);
        List<Order> orderList = this.orderMapper.findUserOrderListByTime(param);
        return result.success("成功",BeanUtils.copy(orderList,OrderVO.class));
    }

}
