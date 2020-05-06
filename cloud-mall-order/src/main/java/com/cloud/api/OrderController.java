package com.cloud.api;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.cloud.common.core.bean.ResponseResult;
import com.cloud.common.core.enums.ResultCodeEnum;
import com.cloud.common.core.service.GenIdService;
import com.cloud.common.core.utils.BeanUtils;
import com.cloud.common.core.utils.JSONUtils;
import com.cloud.dto.order.OrderDTO;
import com.cloud.dto.order.OrderItemDTO;
import com.cloud.dto.order.OrderOperateLogDTO;
import com.cloud.enums.OrderChannel;
import com.cloud.enums.OrderStatus;
import com.cloud.enums.OrderType;
import com.cloud.enums.ShippingMethod;
import com.cloud.exception.OrderBizException;
import com.cloud.model.order.OrderItem;
import com.cloud.model.promotion.Activity;
import com.cloud.model.promotion.Coupon;
import com.cloud.model.promotion.CouponTemplate;
import com.cloud.model.user.ShippingAddress;
import com.cloud.po.order.OrderItemReq;
import com.cloud.po.order.OrderReq;
import com.cloud.po.promotion.CalculateActivityParam;
import com.cloud.po.promotion.CalculateCouponParam;
import com.cloud.service.OrderItemService;
import com.cloud.service.OrderOperateLogService;
import com.cloud.service.OrderService;
import com.cloud.utils.OrderNoGenUtils;
import com.cloud.vo.CaclOrderItem;
import com.cloud.vo.cart.ShoppingCartInfo;
import com.cloud.vo.order.SettleItemVO;
import com.cloud.vo.order.SettleVO;
import com.cloud.vo.order.OrderVO;
import com.cloud.vo.promotion.CouponCaclVO;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;

@RestController
public class OrderController {

    @Autowired
    OrderService orderService;

    @Autowired
    OrderItemService orderItemService;

    @Autowired
    OrderOperateLogService operateLogService;

    @Autowired
    InventoryService inventoryService;

//    @Autowired
//    ShoppingCartService cartService;

    @Autowired
    ActivityService activityService;

    @Autowired
    CouponTemplateService couponTemplateService;

    @Autowired
    CouponService couponService;

    @Autowired
    PromotionService promotionService;

    @Autowired
    GenIdService genIdService;

    @Autowired
    CatalogService catalogService;

    @Autowired
    ShippingAddressService shippingAddressService;

    @RequestMapping(value = "/checkout",method = RequestMethod.GET)
    public ResponseResult<SettleVO> checkout(HttpServletRequest request) {
        String userId = (String)request.getAttribute("userId");
        if (StringUtils.isBlank(userId)) {
            userId = request.getParameter("userId");
            if (StringUtils.isBlank(userId)) {
                throw new OrderBizException(ResultCodeEnum.ORDER_USER_NOT_LOGIN.getCode(),
                        ResultCodeEnum.ORDER_USER_NOT_LOGIN.getMessage());
            }
        }

        ResponseResult<SettleVO> result = new ResponseResult<SettleVO>();

        // 查询用户购物车数据
        ResponseResult<List<ShoppingCartInfo>> cartInfoResult = null;//cartService.findByMemberId(Long.valueOf(userId));
        if (cartInfoResult.getCode() != 200) {
            return result.failed("加载用户购物车信息失败");
        }

        List<ShoppingCartInfo> cartItemList = cartInfoResult.getData();
        if (CollectionUtils.isEmpty(cartItemList)) {
            return result.success("用户购物车为空",null);
        }
        // 获取购物车商品数据

        ShoppingCartInfo cart = cartItemList.get(0);
        SettleVO settleVO = new SettleVO();
        settleVO.setCartId(cart.getCartId());
        settleVO.setMemberId(cart.getMemberId());
        String shippingMethod = request.getParameter("shippingMethod");
        settleVO.setShippingMethod(shippingMethod);

        ResponseResult<List<ShippingAddress>> addressResult = this.shippingAddressService.findShippingAddressListByUserId(Long.valueOf(userId));
        if (addressResult.getCode() != 200 && CollectionUtils.isNotEmpty(addressResult.getData())) {
            List<ShippingAddress> addressList = JSONUtils.convert(JSONObject.toJSONString(addressResult.getData()),new TypeReference<List<ShippingAddress>>(){});
            if (CollectionUtils.isNotEmpty(addressList)) {
                settleVO.setAddressList(addressList);
            }
        }

        List<SettleItemVO> itemList = new ArrayList<>();
        SettleItemVO settleItemVO = null;
        BigDecimal totalAmount = BigDecimal.ZERO;
        BigDecimal totalActSavedMoney = BigDecimal.ZERO;
        // 根据购物车信息，在结算页的时候将购物车信息转化为结算信息，展示在结算页面
        for (ShoppingCartInfo cartInfo : cartItemList) {
            settleItemVO = new SettleItemVO();
            settleItemVO.setSkuId(cartInfo.getSkuId());
            settleItemVO.setSkuName(cartInfo.getSkuName());
            settleItemVO.setSkuPicture(cartInfo.getSkuPicture());
            settleItemVO.setStock(cartInfo.isStock());
            settleItemVO.setUnitPrice(cartInfo.getUnitPrice());
            settleItemVO.setQuantity(cartInfo.getQuantity());
            settleItemVO.setSpecList(cartInfo.getSpecList());
            settleItemVO.setOriginSubTotal(cartInfo.getTotalPrice());
            // 重新计算活动,应该去掉已经到期的活动
            List<Activity> activityList = cartInfo.getActivityList();
            if (checkActivityList(activityList)) {
                return result.failed(String.format("商品%d 有活动到期", settleItemVO.getSkuId()));
            }

            settleItemVO.setActivityList(activityList);
            CalculateActivityParam calculateActivityParam = new CalculateActivityParam();
            calculateActivityParam.setActivityList(activityList);
            calculateActivityParam.setAmount(settleItemVO.getOriginSubTotal());
            calculateActivityParam.setPieces(settleItemVO.getQuantity());
            ResponseResult<BigDecimal> actSavedMoneyResult = this.promotionService.getActListSavedAmount(calculateActivityParam);
            if (actSavedMoneyResult.getCode() != 200) {
                BigDecimal actSavedMoney = actSavedMoneyResult.getData();
                if (actSavedMoney.compareTo(BigDecimal.ZERO) > 0 && actSavedMoney.compareTo(settleItemVO.getOriginSubTotal()) < 0) {
                    settleItemVO.setActSavedMoney(actSavedMoney);
                    totalActSavedMoney = totalActSavedMoney.add(actSavedMoney);
                }
                BigDecimal subTotal = settleItemVO.getOriginSubTotal().subtract(actSavedMoney);
                settleItemVO.setSubTotal(subTotal);
            }

            List<CouponTemplate> templateList = checkCouponTemplateList(cartInfo.getTemplateList());
            settleItemVO.setTemplateList(templateList);

            totalAmount = totalAmount.add(settleItemVO.getOriginSubTotal());
            itemList.add(settleItemVO);
        }
        settleVO.setItemList(itemList);

        // 计算可以扣减的优惠券
        CaclOrderItem caclOrderItem = new CaclOrderItem();
        Map<Long, CaclOrderItem> map = new HashMap<>();
        settleVO.getItemList().forEach(item -> {
            caclOrderItem.setSkuId(item.getSkuId());
            caclOrderItem.setAmount(item.getOriginSubTotal());
            caclOrderItem.setTemplateList(item.getTemplateList());
            map.put(item.getSkuId(), caclOrderItem);
        });
        // 获取用户领取的优惠券
        ResponseResult<List<Coupon>> findUserReceivedCouponListResult = this.couponService.findCouponListByUserId(Long.valueOf(userId));
        List<Coupon> receivedCouponList = null;
        if (findUserReceivedCouponListResult.getCode() == 200) {
            receivedCouponList = findUserReceivedCouponListResult.getData();
        }
        // 获取可以扣减的优惠券
        List<CouponCaclVO> couponCaclList = this.getCouponAmount(map, receivedCouponList);
        BigDecimal totalCouponSavedMoney = BigDecimal.ZERO;
        List<Coupon> usedCouponList = new ArrayList<>();
        // 处理返回的优惠券结果
        couponCaclList.forEach(c -> {
            usedCouponList.add(c.getCoupon());
            totalCouponSavedMoney.add(c.getSavedAmount());
        });
        settleVO.setCouponList(usedCouponList);
        settleVO.setTotalCouponSavedMoney(totalCouponSavedMoney);
        return result.success(settleVO);
    }

    @RequestMapping(value = "/order/createOrder",method = RequestMethod.POST)
    public ResponseResult submitOrder(@RequestBody OrderDTO orderDTO, HttpServletRequest request) {
        ResponseResult result = new ResponseResult();
        if (orderDTO == null) {
            throw new OrderBizException(ResultCodeEnum.ORDER_NULL.getCode(),
                    ResultCodeEnum.ORDER_NULL.getMessage());
        }

        String userId = (String)request.getAttribute("userId");
        if (StringUtils.isBlank(userId)) {
            userId = String.valueOf(orderDTO.getUserId());
            if (StringUtils.isBlank(userId)) {
                throw new OrderBizException(ResultCodeEnum.ORDER_USER_NOT_LOGIN.getCode(),
                        ResultCodeEnum.ORDER_USER_NOT_LOGIN.getMessage());
            }
        }

        return this.orderService.save(orderDTO);
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

    /**
     * 校验优惠券有没有过期的
     * @param templateList
     * @return
     */
    private List<CouponTemplate> checkCouponTemplateList(List<CouponTemplate> templateList) {
        if (CollectionUtils.isEmpty(templateList)) {
            return null;
        }

        List<CouponTemplate> availableTemplateList = new ArrayList<>();
        for (CouponTemplate template : availableTemplateList) {
            LocalDateTime expireTime = template.getExpireTime();
            if (LocalDateTime.now().isBefore(expireTime)) {
                availableTemplateList.add(template);
            }
        }
        return availableTemplateList;
    }

    /**
     * 根据商品的活动列表、商品总金额和商品件数计算该商品的优惠金额
     * @param activityList
     * @param amount
     * @param quantity
     * @return
     */
    private BigDecimal getActListSavedAmount(List<Activity> activityList, BigDecimal amount, int quantity) {
        if (CollectionUtils.isEmpty(activityList)) {
            return BigDecimal.ZERO;
        }
        CalculateActivityParam param = new CalculateActivityParam();
        param.setActivityList(activityList);
        param.setAmount(amount);
        param.setPieces(quantity);
        ResponseResult result = this.promotionService.getActListSavedAmount(param);
        if (result.getCode() != 200) {
            return BigDecimal.ZERO;
        }
        return (BigDecimal) result.getData();
    }

    /**
     * 根据用户领取的优惠券开始计算优惠券
     * @param items
     * @param couponList
     * @return
     */
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
        List<Coupon> candidateList = new ArrayList<>();

        List<CalculateCouponParam> paramList = new ArrayList<>();
        for (Map.Entry<Long,CaclOrderItem> entry : items.entrySet()) {
            Long skuId = entry.getKey();
            // 将该skuId对应的用户领取的优惠券转化成参数
            Set<Long> templateIds = new HashSet<>();
            items.forEach((k,v) -> {
                v.getTemplateList().forEach(t -> {
                    templateIds.add(t.getTemplateId());
                });
            });
            // 校验当前SKU优惠券信息是否可以参加优惠计算，将满足条件的放到候选人列表,只有没有过期的而且用户领取了的
            // 才有资格参加优惠券计算
            for (Coupon coupon : couponList) {
                if (templateIds.contains(coupon.getTemplate().getTemplateId())) {
                    candidateList.add(coupon);
                }
            }
            CalculateCouponParam param = new CalculateCouponParam();
            param.setSkuId(entry.getValue().getSkuId());
            param.setAmount(entry.getValue().getAmount());
            param.setCouponList(candidateList);
            paramList.add(param);
        }
        ResponseResult<List<CouponCaclVO>> couponResult = this.promotionService.getAvailableCouponInfo(paramList);
        if (couponResult.getCode() != 200) {
            return null;
        }
        List<CouponCaclVO> couponCaclList = JSONUtils.convert(JSONObject.toJSONString(couponResult.getData()),
                new TypeReference<List<CouponCaclVO>>(){});
        return couponCaclList;
    }

    @RequestMapping(value = "/order/findOrderById", method = RequestMethod.GET)
    public ResponseResult<OrderVO> findOrderById(@RequestParam("orderId") Long orderId) {
        return this.orderService.findOrderById(orderId);
    }

    @RequestMapping(value = "/order/cancelOrder", method = RequestMethod.GET)
    public ResponseResult cancelOrder(@RequestParam("orderNo") Long orderNo) {
//        OrderVO order = this.orderService.findOrderByNo(orderNo);
//        if (order == null) {
//            throw new OrderBizException(String.format("%s: 该订单不存在",orderNo));
//        }
//        // 根据订单状态,处理订单的取消(状态模式)
//        if (order.getStatus() == OrderStatus.WAITING_PAYMENT.getCode()) {
//            order.setStatus(OrderStatus.CANCELLED.getCode());
//            this.orderService.update(BeanUtils.copy(order,OrderDTO.class));
//        } else if (order.getStatus() == OrderStatus.WAITING_DELIVERY.getCode()) {
//            order.setStatus(OrderStatus.CANCELLED.getCode());
//            this.orderService.update(BeanUtils.copy(order,OrderDTO.class));
//        } else if (order.getStatus() == OrderStatus.WAITING_RECEIVE.getCode()){
//            // 则不能直接关闭，需要联系售后进行人工干预
//        }
//        ResponseResult result = new ResponseResult();
//        return result.success("成功",null);
        return null;
    }

    @RequestMapping(value = "/order/batchAdd", method = RequestMethod.POST)
    public int batchAddOrder(@RequestParam("start") Long start, @RequestParam("end") Long end, @RequestParam("orderNum") int orderNum) {
        return 0;
    }

}
