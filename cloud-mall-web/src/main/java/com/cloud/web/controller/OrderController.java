package com.cloud.web.controller;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.cloud.api.*;
import com.cloud.common.core.bean.ResponseResult;
import com.cloud.common.core.utils.BeanUtils;
import com.cloud.common.core.utils.JSONUtils;
import com.cloud.common.service.RestService;
import com.cloud.dto.order.OrderDTO;
import com.cloud.vo.catalog.ItemSpec;
import com.cloud.model.catalog.Sku;
import com.cloud.model.promotion.Activity;
import com.cloud.model.promotion.Coupon;
import com.cloud.model.promotion.CouponTemplate;
import com.cloud.po.order.OrderItemReq;
import com.cloud.po.order.OrderReq;
import com.cloud.po.promotion.CalculateActivityParam;
import com.cloud.po.promotion.CalculateCouponParam;
import com.cloud.vo.order.CaclOrderItem;
import com.cloud.vo.order.OrderVO;
import com.cloud.vo.promotion.CouponCaclVO;
import com.cloud.web.bean.OrderSubmitReq;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.MapUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Controller;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.*;

@Slf4j
@Controller
public class OrderController {

    @Autowired
    RestService restService;

    @Autowired
    CatalogService catalogService;

    @Autowired
    CouponService couponService;

    @Autowired
    CouponTemplateService templateService;

    @Autowired
    ActivityService activityService;

    @Autowired
    PromotionService promotionService;

    @Autowired
    OrderService orderService;

    public ModelAndView submitOrder(@RequestBody OrderReq orderParam, HttpServletRequest request) {
        ModelAndView mv = new ModelAndView();
        MultiValueMap<String,Object> params = new LinkedMultiValueMap<>();
        params.add("orderParam",orderParam);
        JSONObject createOrderJSON = this.restService.exchange("http://api.cloud.com/order/createOrder", HttpMethod.POST,
                JSONObject.class,params,null);
        TypeReference<ResponseResult<OrderVO>> createOrderResultType = new TypeReference<ResponseResult<OrderVO>>(){};
        ResponseResult<OrderVO> createOrderResult = createOrderJSON.toJavaObject(createOrderResultType);
        if (createOrderResult.getCode() != 200) {
            mv.addObject("errMsg",createOrderResult.getMsg());
            mv.setViewName("redirect:/order/confirm_order");
        }
        OrderVO orderVO = createOrderResult.getData();
        mv.addObject("orderDTO", BeanUtils.copy(orderVO, OrderDTO.class));
        // 这个页面
        mv.setViewName("/order/payment");
        return mv;
    }

    @RequestMapping(value = "/order/submit",method = RequestMethod.POST)
    public ResponseResult createOrder(@RequestBody OrderSubmitReq orderSubmitReq) {
        OrderItemReq itemReq = null;
        BigDecimal totalActSavedMoney = BigDecimal.ZERO;
        BigDecimal totalAmount = BigDecimal.ZERO;
        List<OrderItemReq> itemList = new ArrayList<>();
        for (Long skuId : orderSubmitReq.getSkuList()) {
            ResponseResult skuResult = this.catalogService.findBySkuId(skuId);
            Sku sku = JSONUtils.convert(JSONObject.toJSONString(skuResult.getData()), new TypeReference<Sku>() {
            });
            if (sku == null) {
                continue;
            }
            itemReq = new OrderItemReq();
            itemReq.setSkuId(sku.getId());
            itemReq.setSkuName(sku.getName());
            itemReq.setSkuPicture(sku.getImageUrl());
            itemReq.setStock(true);
            itemReq.setUnitPrice(BigDecimal.valueOf(sku.getSalePrice() / 100));
            itemReq.setQuantity(1);
            itemReq.setOriginSubTotal(itemReq.getUnitPrice().multiply(BigDecimal.valueOf(itemReq.getQuantity())));
            totalAmount = totalAmount.add(itemReq.getOriginSubTotal());
            ResponseResult itemSpecListResult = this.catalogService.findItemSpecList(skuId);
            List<ItemSpec> specList = JSONUtils.convert(JSONObject.toJSONString(itemSpecListResult.getData()), new TypeReference<List<ItemSpec>>() {
            });
            if (CollectionUtils.isNotEmpty(specList)) {
                itemReq.setSpecList(specList);
            }
            ResponseResult<List<Activity>> actListResult = this.activityService.findBySkuId(skuId);
            List<Activity> activityList = JSONUtils.convert(JSONObject.toJSONString(actListResult.getData()), new TypeReference<List<Activity>>() {
            });
            itemReq.setActivityList(activityList);

            CalculateActivityParam calculateActivityParam = new CalculateActivityParam();
            calculateActivityParam.setActivityList(activityList);
            calculateActivityParam.setAmount(itemReq.getOriginSubTotal());
            calculateActivityParam.setPieces(itemReq.getQuantity());
            ResponseResult<BigDecimal> actSavedMoneyResult = this.promotionService.getActListSavedAmount(calculateActivityParam);
            if (actSavedMoneyResult.getCode() == 200) {
                BigDecimal actSavedMoney = actSavedMoneyResult.getData();
                if (actSavedMoney.compareTo(BigDecimal.ZERO) > 0 && actSavedMoney.compareTo(itemReq.getOriginSubTotal()) < 0) {
                    itemReq.setActivitySavedMoney(actSavedMoney);
                    totalActSavedMoney = totalActSavedMoney.add(actSavedMoney);
                }
                BigDecimal subTotal = itemReq.getOriginSubTotal().subtract(actSavedMoney);
                itemReq.setSubTotal(subTotal);
            }

            ResponseResult<List<CouponTemplate>> templatesResult = templateService.findAllAvailableTemplateList(skuId);
            List<CouponTemplate> templateList = JSONUtils.convert(JSONObject.toJSONString(templatesResult.getData()),
                    new TypeReference<List<CouponTemplate>>() {
                    });
            itemReq.setTemplateList(templateList);
            itemList.add(itemReq);
        }

        OrderReq orderReq = new OrderReq();
        orderReq.setMemberId(orderSubmitReq.getUserId());
        orderReq.setAddressId(164394251973571642L);
        orderReq.setShippingMethod("快递");
        orderReq.setType("标准");
        orderReq.setSource("平板");
        orderReq.setItemList(itemList);
        orderReq.setActivityAmount(totalActSavedMoney);
        // 计算可以扣减的优惠券
        Map<Long, CaclOrderItem> map = new HashMap<>();
        orderReq.getItemList().forEach(item -> {
            CaclOrderItem caclOrderItem = new CaclOrderItem();
            caclOrderItem.setSkuId(item.getSkuId());
            caclOrderItem.setAmount(item.getOriginSubTotal());
            caclOrderItem.setTemplateList(item.getTemplateList());
            map.put(item.getSkuId(), caclOrderItem);
        });
        // 获取用户领取的优惠券
        ResponseResult<List<Coupon>> findUserReceivedCouponListResult =
                this.couponService.findCouponListByUserId(Long.valueOf(orderSubmitReq.getUserId()));
        List<Coupon> receivedCouponList = null;
        if (findUserReceivedCouponListResult.getCode() == 200) {
            receivedCouponList = findUserReceivedCouponListResult.getData();
        }
        // 获取可以扣减的优惠券
        List<CouponCaclVO> couponCaclList = this.getCouponAmount(map, receivedCouponList);
        log.info(JSONObject.toJSONString(couponCaclList));
        if (CollectionUtils.isNotEmpty(couponCaclList)) {
            BigDecimal totalCouponSavedMoney = BigDecimal.ZERO;
            List<Coupon> usedCouponList = new ArrayList<>();
            // 处理返回的优惠券结果
            for (CouponCaclVO couponCacl : couponCaclList) {
                usedCouponList.add(couponCacl.getCoupon());
                totalCouponSavedMoney = totalCouponSavedMoney.add(couponCacl.getSavedAmount());
            }
            orderReq.setCouponList(usedCouponList);
            orderReq.setCouponAmount(totalCouponSavedMoney);
        } else {
            orderReq.setCouponAmount(BigDecimal.ZERO);
        }
        orderReq.setTotalAmount(totalAmount);
        return this.orderService.submitOrder(orderReq);
    }

    private List<CouponCaclVO> getCouponAmount(Map<Long,CaclOrderItem> items, List<Coupon> couponList) {
        // 商品参数为空
        if (MapUtils.isEmpty(items)) {
            return null;
        }

        // 用户没有任何优惠券
        if (CollectionUtils.isEmpty(couponList)) {
            return null;
        }

        // 但是我不知道哪些用户优惠券应该参加计算啊
        // 获取候选的优惠券
        List<Coupon> candidateList = null;
        List<CalculateCouponParam> paramList = new ArrayList<>();
        CalculateCouponParam calculateCouponParam = null;
        for (Map.Entry<Long,CaclOrderItem> entry : items.entrySet()) {
            // sku 对应的优惠券信息
            Long skuId = entry.getKey();
            CaclOrderItem item = entry.getValue();
            List<Long> skuTemplateList = new ArrayList<>();
            item.getTemplateList().forEach(c -> {
                skuTemplateList.add(c.getTemplateId());
            });
            List<Long> receivedList = new ArrayList<>();
            couponList.forEach(c -> {
                receivedList.add(c.getTemplate().getTemplateId());
            });
            // 获取SKU对应的优惠券模板和用户领取的优惠券的模板的交集，才是参加运算的优惠券
            // 如果SKU没有对应的优惠券模板，但是用户领取的有，那么当前SKU是不可以用的
            // 如果SKU有优惠券模板，但是用户没有领取，也是不行的
            List<Long> intersection = (List<Long>)CollectionUtils.intersection(skuTemplateList,receivedList);
            // 获取优惠券模板对应的用户领取的优惠券
            candidateList = new ArrayList<>();
            for (Coupon coupon : couponList) {
                if (intersection.contains(coupon.getTemplate().getTemplateId())) {
                    candidateList.add(coupon);
                }
            }
            calculateCouponParam = new CalculateCouponParam();
            calculateCouponParam.setSkuId(item.getSkuId());
            calculateCouponParam.setAmount(item.getAmount());
            calculateCouponParam.setCouponList(candidateList);
            paramList.add(calculateCouponParam);
        }
        ResponseResult<List<CouponCaclVO>> couponResult = this.promotionService.getAvailableCouponInfo(paramList);
        if (couponResult.getCode() != 200) {
            return null;
        }
        List<CouponCaclVO> couponCaclList = JSONUtils.convert(JSONObject.toJSONString(couponResult.getData()),
                new TypeReference<List<CouponCaclVO>>(){});
        return couponCaclList;
    }
}