package com.cloud.service.impl;

import com.cloud.api.*;
import com.cloud.common.core.bean.ResponseResult;
import com.cloud.dto.order.OrderDTO;
import com.cloud.dto.order.OrderItemDTO;
import com.cloud.model.order.OrderItem;
import com.cloud.model.promotion.Activity;
import com.cloud.model.promotion.Coupon;
import com.cloud.service.OrderAggregationService;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class OrderAggregationServiceImpl implements OrderAggregationService {

    @Autowired
    OrderService orderService;

    @Autowired
    InventoryService inventoryService;

    @Autowired
    PromotionService promotionService;

    @Autowired
    ActivityService activityService;

    @Autowired
    CouponService couponService;

    @Autowired
    CatalogService catalogService;

    @Override
    public ResponseResult createOrder(OrderDTO orderDTO) {
        ResponseResult result = new ResponseResult();
        List<OrderItemDTO> orderItemDTOList = orderDTO.getItemList();
        for (OrderItemDTO itemDTO : orderItemDTOList) {
            // 预占库存
            ResponseResult preOccupyStockResult = this.inventoryService.preOccupyStock(itemDTO.getSkuId(),
                    itemDTO.getQuantity());
            if (preOccupyStockResult.getCode() != 200) {
                return preOccupyStockResult;
            }

            // 获取商品价格，有可能价格改变了
            ResponseResult getPriceResult = this.catalogService.getPrice(itemDTO.getSkuId());
            if (getPriceResult.getCode() != 200 ||
                    BigDecimal.valueOf((Integer)getPriceResult.getData()).compareTo(itemDTO.getUnitPrice()) != 0) {
                return result.failed("商品价格发生了变动");
            }

            // 检查有无活动到期，如果有不该提交订单
            List<Activity> activityList = itemDTO.getActivityList();
            if (!checkActivityList(activityList)){
                return result.failed(String.format("商品%d 有活动到期",itemDTO.getSkuId()));
            }
        }

        // 预占优惠券
        List<Coupon> couponList = orderDTO.getCouponList();
        if (CollectionUtils.isNotEmpty(couponList) && orderDTO.getCouponAmount()
                .compareTo(BigDecimal.ZERO) > 0) {
            ResponseResult preUseCouponResult = this.couponService.preUseCoupon(couponList);
            if (preUseCouponResult.getCode() != 200) {
                return preUseCouponResult;
            }
        }

        ResponseResult createOrderResult = this.orderService.createOrder(orderDTO);
        if (createOrderResult.getCode() != 200) {
            return createOrderResult;
        }

        return result.success("成功",200);
    }

    /**
     * 检查活动有没有过期的
     * @param activityList
     * @return
     */
    private boolean checkActivityList(List<Activity> activityList) {
        if (CollectionUtils.isEmpty(activityList)) {
            return Boolean.FALSE;
        }

        for (Activity activity : activityList) {
            LocalDateTime endTime = activity.getEndTime();
            if (LocalDateTime.now().isAfter(endTime)) {
                return Boolean.FALSE;
            }
        }
        return Boolean.TRUE;
    }
}
