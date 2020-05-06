package com.cloud.service.impl;

import com.cloud.common.core.utils.BeanUtils;
import com.cloud.common.redis.service.RedisService;
import com.cloud.dto.cart.ShoppingCartDTO;
import com.cloud.exception.CartBizException;
import com.cloud.mapper.ShoppingCartMapper;
import com.cloud.model.cart.ShoppingCart;
import com.cloud.service.ShoppingCartService;
import com.cloud.vo.cart.ShoppingCartInfo;
import com.google.common.collect.Sets;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ShoppingCartServiceImpl implements ShoppingCartService {

    @Autowired
    ShoppingCartMapper shoppingCartMapper;

    @Autowired
    RedisService redisService;

    private static final int MEMBER_CART_LIMIT = 200;

    @Override
    public int save(ShoppingCartDTO shoppingCartDTO) {
        if (shoppingCartDTO.getQuantity() > MEMBER_CART_LIMIT) {
            throw new CartBizException("添加商品数量不能超过200");
        }
        ShoppingCart shoppingCart = BeanUtils.copy(shoppingCartDTO,ShoppingCart.class);
        return this.shoppingCartMapper.save(shoppingCart);
    }

    @Override
    public int saveList(List<ShoppingCart> cartList) {
        return this.shoppingCartMapper.saveList(cartList);
    }

    @Override
    public int update(ShoppingCartDTO shoppingCartDTO) {
        ShoppingCart shoppingCart = BeanUtils.copy(shoppingCartDTO,ShoppingCart.class);
        return this.shoppingCartMapper.update(shoppingCart);
    }

    @Override
    public int delete(Long cartId) {
        return this.shoppingCartMapper.delete(cartId);
    }

    @Override
    public int deleteCartByMemberId(Long memberId) {
        return this.shoppingCartMapper.deleteCartByMemberId(memberId);
    }

    @Override
    public int deleteCartBySkuId(Long skuId) {
        return this.shoppingCartMapper.deleteCartBySkuId(skuId);
    }

    @Override
    public ShoppingCartInfo find(Long cartId) {
        ShoppingCart cart = this.shoppingCartMapper.find(cartId);
        ShoppingCartInfo cartInfo = BeanUtils.copy(cart,ShoppingCartInfo.class);
//        ResponseResult<List<Activity>> activityResults = this.activityService.findBySkuId(cartInfo.getSkuId());
//        if (activityResults.getCode() == ResultCodeEnum.OK.getCode()) {
//            List<Activity> activities = activityResults.getData();
//            if (CollectionUtils.isNotEmpty(activities)) {
//                cartInfo.setActivityList(activities);
//            }
//        }
//        ResponseResult<List<CouponTemplate>> templateResults = this.couponTemplateService.findBySkuId(cartInfo.getSkuId());
//        if (templateResults.getCode() == ResultCodeEnum.OK.getCode()) {
//            List<CouponTemplate> templates = templateResults.getData();
//            if (CollectionUtils.isNotEmpty(templates)) {
//                cartInfo.setTemplateList(templates);
//            }
//        }
        return cartInfo;
    }

    @Override
    public List<ShoppingCartInfo> findCartByMemberId(Long memberId) {
        List<ShoppingCart> cartList = this.shoppingCartMapper.findCartByMemberId(memberId);
        if (CollectionUtils.isEmpty(cartList)) {
            return null;
        }

        List<ShoppingCartInfo> memberCartList = BeanUtils.copy(cartList,ShoppingCartInfo.class);
        // 1 456 11 12
        // 2 567 11
        // 3 245 13
//        for (ShoppingCartInfo cartInfo : memberCartList) {
//            ResponseResult<List<Activity>> activityResults = this.activityService.findBySkuId(cartInfo.getSkuId());
//            if (activityResults.getCode() == ResultCodeEnum.OK.getCode()) {
//                List<Activity> activities = activityResults.getData();
//                if (CollectionUtils.isNotEmpty(activities)) {
//                    cartInfo.setActivityList(activities);
//                }
//            }
//            ResponseResult<List<CouponTemplate>> templateResults = this.couponTemplateService.findBySkuId(cartInfo.getSkuId());
//            if (templateResults.getCode() == ResultCodeEnum.OK.getCode()) {
//                List<CouponTemplate> templates = templateResults.getData();
//                if (CollectionUtils.isNotEmpty(templates)) {
//                    cartInfo.setTemplateList(templates);
//                }
//            }
//        }
        return memberCartList;
    }

    @Override
    public List<ShoppingCart> findAll() {
        return this.shoppingCartMapper.findAll();
    }

    @Override
    public void addCartToRedis(Long memberId, List<ShoppingCartInfo> cartList) {

        this.redisService.set("cloud-mall-cart:member:"+memberId,cartList);
    }

    @Override
    public List<ShoppingCartInfo> getCartFromRedis(Long memberId) {

        return (List<ShoppingCartInfo>)this.redisService.get("cloud-mall-cart:member:"+memberId);
    }

    @Override
    public int mergeShoppingCart(Long memberId, List<ShoppingCartDTO> redisCartList, List<ShoppingCartDTO> clientCartLists) {
        // 如果客户端没有购物车，比如cookie或者app没有，则不需要合并，直接返回
        if (CollectionUtils.isEmpty(clientCartLists)) {
            return 1;
        }

        List<ShoppingCart> srcList = BeanUtils.copy(clientCartLists,ShoppingCart.class);
        List<ShoppingCart> destList = BeanUtils.copy(clientCartLists,ShoppingCart.class);
        Set<ShoppingCart> redisCartSet = new HashSet<ShoppingCart>();
        redisCartSet.addAll(destList);
        Set<ShoppingCart> clientCartSet = new HashSet<ShoppingCart>();
        clientCartSet.addAll(srcList);
        Set<ShoppingCart> differenceList = Sets.difference(clientCartSet,redisCartSet);
        List<ShoppingCart> targetCartList = new ArrayList<>(differenceList);
        int code = this.shoppingCartMapper.saveList(targetCartList);
        if (code == 0) {
            return code;
        }
        srcList.addAll(targetCartList);
        addCartToRedis(memberId,BeanUtils.copy(srcList,ShoppingCartInfo.class));
        return code;
    }
}
