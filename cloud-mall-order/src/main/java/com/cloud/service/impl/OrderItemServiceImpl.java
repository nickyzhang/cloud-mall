package com.cloud.service.impl;

import com.cloud.common.core.bean.ResponseResult;
import com.cloud.common.core.service.GenIdService;
import com.cloud.common.core.utils.BeanUtils;
import com.cloud.dto.order.OrderItemDTO;
import com.cloud.mapper.OrderItemMapper;
import com.cloud.model.order.OrderItem;
import com.cloud.service.OrderItemService;
import com.cloud.vo.order.OrderItemVO;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class OrderItemServiceImpl implements OrderItemService {

    @Autowired
    OrderItemMapper itemMapper;

    @Autowired
    GenIdService genIdService;

    @Override
    public ResponseResult save(OrderItemDTO itemDTO) {
        ResponseResult result = new ResponseResult();
        if (itemDTO == null) {
            return result.failed("订单项为空");
        }
        OrderItem item = BeanUtils.copy(itemDTO,OrderItem.class);
        item.setItemId(this.genIdService.genId());
        LocalDateTime now = LocalDateTime.now();
        item.setCreateTime(now);
        item.setUpdateTime(now);
        int code = this.itemMapper.save(item);
        return code == 0 ? result.failed("保存订单项失败") : result.success("成功",null);
    }

    @Override
    public ResponseResult saveList(List<OrderItem> itemList) {
        ResponseResult result = new ResponseResult();
        if (CollectionUtils.isEmpty(itemList)) {
            return result.failed("要保存的订单项列表为空");
        }
        int code = this.itemMapper.saveList(itemList);
        return code == 0 ? result.failed("保存订单项列表失败") : result.success("成功",null);
    }

    @Override
    public ResponseResult update(OrderItemDTO itemDTO) {
        ResponseResult result = new ResponseResult();
        if (itemDTO == null) {
            return result.failed("订单项为空");
        }
        OrderItem item = BeanUtils.copy(itemDTO,OrderItem.class);
        item.setUpdateTime(LocalDateTime.now());
        int code = this.itemMapper.update(item);
        return code == 0 ? result.failed("更新订单项失败") : result.success("成功",null);
    }

    @Override
    public ResponseResult delete(Long itemId) {
        ResponseResult result = new ResponseResult();
        int code = this.itemMapper.delete(itemId);
        return code == 0 ? result.failed("删除订单项失败") : result.success("成功",null);
    }

    @Override
    public ResponseResult<OrderItemVO> findOrderItemById(Long itemId) {
        ResponseResult<OrderItemVO> result = new ResponseResult<>();
        OrderItem item = this.itemMapper.findOrderItemById(itemId);
        return result.success("成功",BeanUtils.copy(item,OrderItemVO.class));
    }

    @Override
    public ResponseResult<List<OrderItemVO>> findOrderItemByOrderId(Long orderId) {
        ResponseResult<List<OrderItemVO>> result = new ResponseResult<>();
        List<OrderItem> itemList = this.itemMapper.findOrderItemByOrderId(orderId);
        return result.success("成功",BeanUtils.copy(itemList,OrderItemVO.class));
    }

    @Override
    public ResponseResult<List<OrderItemVO>>findOrderItemByOrderNo(Long orderNo) {
        ResponseResult<List<OrderItemVO>> result = new ResponseResult<>();
        List<OrderItem> itemList = this.itemMapper.findOrderItemByOrderNo(orderNo);
        return result.success("成功",BeanUtils.copy(itemList,OrderItemVO.class));
    }
}
