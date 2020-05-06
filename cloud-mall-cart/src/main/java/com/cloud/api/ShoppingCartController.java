package com.cloud.api;

import com.cloud.common.core.bean.ResponseResult;
import com.cloud.common.core.enums.ResultCodeEnum;
import com.cloud.common.core.service.GenIdService;
import com.cloud.common.core.utils.BeanUtils;
import com.cloud.dto.cart.ShoppingCartDTO;
import com.cloud.exception.CartBizException;
import com.cloud.vo.catalog.ItemSpec;
import com.cloud.model.promotion.Activity;
import com.cloud.model.promotion.CouponTemplate;
import com.cloud.po.cart.ShoppingCartParam;
import com.cloud.service.ShoppingCartService;
import com.cloud.vo.cart.ShoppingCartInfo;
import com.cloud.vo.catalog.SkuVo;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
public class ShoppingCartController {
    @Autowired
    GenIdService genIdService;

    @Autowired
    ShoppingCartService shoppingCartService;

    @Autowired
    CatalogService catalogService;

    @Autowired
    PromotionService promotionService;

    @Autowired
    ActivityService activityService;

    @Autowired
    CouponTemplateService couponTemplateService;

    @RequestMapping(value = "/cart/addToCart",method = RequestMethod.POST)
    public ResponseResult addToCart(@RequestParam("skuId") Long skuId, @RequestParam Integer count,
                                    HttpServletRequest request) {
        if (skuId == null) {
            throw new CartBizException("要添加到购物车的的商品的skuId为空");
        }

        if (count == null) {
            throw new CartBizException("要添加到购物车的商品数量为空");
        }

        if (count <= 0) {
            throw new CartBizException("要添加到购物车的商品数量小于或者等于0");
        }

        ResponseResult result = this.catalogService.findBySkuId(skuId);
        if (result.getCode() != ResultCodeEnum.OK.getCode()) {
            return result.failed("不能获取该商品");
        }

        String userId = (String)request.getAttribute("userId");
        SkuVo skuVo = (SkuVo) result.getData();
        ShoppingCartDTO shoppingCartDTO = new ShoppingCartDTO();
        shoppingCartDTO.setCartId(this.genIdService.genId());
        shoppingCartDTO.setSkuId(skuId);
        if (StringUtils.isNotBlank(userId)) {
            shoppingCartDTO.setMemberId(Long.valueOf(userId));
        }
        shoppingCartDTO.setQuantity(count);
        shoppingCartDTO.setSkuName(skuVo.getName());
        shoppingCartDTO.setSkuPicture(skuVo.getImageUrl());
        shoppingCartDTO.setUnitPrice(BigDecimal.valueOf(skuVo.getSalePrice()/100));
        shoppingCartDTO.setBeforeUnitPrice(BigDecimal.valueOf(skuVo.getSalePrice()/100));
        shoppingCartDTO.setPriceChange(shoppingCartDTO.getUnitPrice().subtract(shoppingCartDTO.getBeforeUnitPrice()));
        shoppingCartDTO.setDeleted(Boolean.FALSE);
        LocalDateTime now = LocalDateTime.now();
        shoppingCartDTO.setCreateTime(now);
        shoppingCartDTO.setUpdateTime(now);

        int code = this.shoppingCartService.save(shoppingCartDTO);
        if (code == 0) {
            return result.failed("添加购物车失败");
        }

        ShoppingCartInfo shoppingCartInfo = BeanUtils.copy(shoppingCartDTO,ShoppingCartInfo.class);
        // 查询 规格
        ResponseResult<Map<String,Object>> skuSaleAttributesResult = this.catalogService.
                findSkuSaleAttributes(shoppingCartDTO.getSkuId());
        if (skuSaleAttributesResult.getCode() == ResultCodeEnum.OK.getCode()) {
            List<ItemSpec> specList = new ArrayList<>();
            Map<String,Object> map = skuSaleAttributesResult.getData();
            map.forEach((key,val) -> {
                ItemSpec spec = new ItemSpec();
                spec.setSkuId(shoppingCartDTO.getSkuId());
                spec.setAttrName(key);
                spec.setAttrValue(val);
                specList.add(spec);
            });
            shoppingCartInfo.setSpecList(specList);
        }

        ResponseResult<List<Activity>> activityResults = this.activityService.findBySkuId(shoppingCartInfo.getSkuId());
        if (activityResults.getCode() == ResultCodeEnum.OK.getCode()) {
            List<Activity> activities = activityResults.getData();
            if (CollectionUtils.isNotEmpty(activities)) {
                shoppingCartInfo.setActivityList(activities);
                ResponseResult savedMoneyResult = this.promotionService.calcActivityRule(shoppingCartInfo.getUnitPrice(),shoppingCartInfo.getQuantity(),
                        activities.get(0).getActivityId());
                if (savedMoneyResult.getCode() == 200) {
                    shoppingCartInfo.setActSavedMoney((BigDecimal) savedMoneyResult.getData());
                }
            }
        }
        ResponseResult<List<CouponTemplate>> templateResults = this.couponTemplateService.findAllAvailableTemplateList(shoppingCartInfo.getSkuId());
        if (templateResults.getCode() == ResultCodeEnum.OK.getCode()) {
            List<CouponTemplate> templates = templateResults.getData();
            if (CollectionUtils.isNotEmpty(templates)) {
                shoppingCartInfo.setTemplateList(templates);
            }
        }

        List<ShoppingCartInfo> redisCartInfo = this.shoppingCartService.getCartFromRedis(shoppingCartInfo.getMemberId());
        redisCartInfo.add(shoppingCartInfo);

        this.shoppingCartService.addCartToRedis(shoppingCartInfo.getMemberId(),redisCartInfo);
        return result.success("添加购物车成功",null);
    }

    @RequestMapping(value = "/cart/add",method = RequestMethod.POST)
    public ResponseResult add(@RequestBody ShoppingCartParam cart) {
        ResponseResult result = new ResponseResult();
        if (cart == null) {
            return result.failed("要添加的购物车为空");
        }

        ShoppingCartDTO shoppingCartDTO = BeanUtils.copy(cart,ShoppingCartDTO.class);
        shoppingCartDTO.setCartId(this.genIdService.genId());
        shoppingCartDTO.setDeleted(Boolean.FALSE);
        LocalDateTime now = LocalDateTime.now();
        shoppingCartDTO.setCreateTime(now);
        shoppingCartDTO.setUpdateTime(now);
        int code = this.shoppingCartService.save(shoppingCartDTO);
        if (code == 0) {
            return result.failed("添加购物车失败");
        }

        ShoppingCartInfo shoppingCartInfo = BeanUtils.copy(shoppingCartDTO,ShoppingCartInfo.class);
        // 查询 规格
        ResponseResult<Map<String,Object>> skuSaleAttributesResult = this.catalogService.
                findSkuSaleAttributes(shoppingCartDTO.getSkuId());
        if (skuSaleAttributesResult.getCode() == ResultCodeEnum.OK.getCode()) {
            List<ItemSpec> specList = new ArrayList<>();
            Map<String,Object> map = skuSaleAttributesResult.getData();
            map.forEach((key,val) -> {
                ItemSpec spec = new ItemSpec();
                spec.setSkuId(shoppingCartDTO.getSkuId());
                spec.setAttrName(key);
                spec.setAttrValue(val);
                specList.add(spec);
            });
            shoppingCartInfo.setSpecList(specList);
        }

        ResponseResult<List<Activity>> activityResults = this.activityService.findBySkuId(shoppingCartInfo.getSkuId());
        if (activityResults.getCode() == ResultCodeEnum.OK.getCode()) {
            List<Activity> activities = activityResults.getData();
            if (CollectionUtils.isNotEmpty(activities)) {
                shoppingCartInfo.setActivityList(activities);

            }
        }
        ResponseResult<List<CouponTemplate>> templateResults = this.couponTemplateService.findAllAvailableTemplateList(shoppingCartInfo.getSkuId());
        if (templateResults.getCode() == ResultCodeEnum.OK.getCode()) {
            List<CouponTemplate> templates = templateResults.getData();
            if (CollectionUtils.isNotEmpty(templates)) {
                shoppingCartInfo.setTemplateList(templates);
            }
        }

        List<ShoppingCartInfo> redisCartInfo = this.shoppingCartService.getCartFromRedis(shoppingCartInfo.getMemberId());
        redisCartInfo.add(shoppingCartInfo);

        this.shoppingCartService.addCartToRedis(shoppingCartInfo.getMemberId(),redisCartInfo);
        return result.success("添加购物车成功",null);
    }


    @RequestMapping(value = "/cart/update",method = RequestMethod.PUT)
    public ResponseResult update(@RequestBody ShoppingCartParam cart) {
        ResponseResult result = new ResponseResult();
        if (cart == null) {
            return result.failed("要更新的购物车为空");
        }

        ShoppingCartDTO shoppingCartDTO = BeanUtils.copy(cart,ShoppingCartDTO.class);
        shoppingCartDTO.setUpdateTime(LocalDateTime.now());
        int code = this.shoppingCartService.update(shoppingCartDTO);
        return code == 0 ? result.failed("更新购物车失败") : result.success("更新购物车成功",null);
    }

    @RequestMapping(value = "/cart/delete/{cartId}",method = RequestMethod.POST)
    public ResponseResult delete(@PathVariable("cartId") Long cartId) {
        ResponseResult result = new ResponseResult();
        if (cartId == null) {
            return result.failed("要删除的购物车id为空");
        }
        int code = this.shoppingCartService.delete(cartId);
        return code == 0 ? result.failed("删除购物车失败") : result.success("删除购物车成功",null);
    }

    @RequestMapping(value = "/cart/deleteBySkuId",method = RequestMethod.POST)
    public ResponseResult deleteBySkuId(@RequestParam Long skuId) {
        ResponseResult result = new ResponseResult();
        if (skuId == null) {
            return result.failed("要删除的购物车id为空");
        }
        int code = this.shoppingCartService.deleteCartBySkuId(skuId);
        return code == 0 ? result.failed("删除购物车失败") : result.success("删除购物车成功",null);
    }

    @RequestMapping(value = "/cart/deleteByMemberId",method = RequestMethod.POST)
    public ResponseResult deleteByMemberId(@RequestParam Long memberId) {
        ResponseResult result = new ResponseResult();
        if (memberId == null) {
            return result.failed("要删除的购物车为空");
        }
        int code = this.shoppingCartService.delete(memberId);
        return code == 0 ? result.failed("删除购物车失败") : result.success("删除购物车成功",null);
    }

    @RequestMapping(value = "/cart/list/{cartId}",method = RequestMethod.GET)
    public ResponseResult<ShoppingCartInfo> findById(@PathVariable("cartId") Long cartId) {
        ResponseResult result = new ResponseResult();
        if (cartId == null) {
            return result.failed("要查询的购物车id为空");
        }
        ShoppingCartInfo shoppingCartInfo = this.shoppingCartService.find(cartId);
        return shoppingCartInfo == null ? result.success("不存在这个购物车id") :
                result.success("查询成功",shoppingCartInfo);
    }

    @RequestMapping(value = "/cart/findByMemberId",method = RequestMethod.GET)
    public ResponseResult<List<ShoppingCartInfo>> findByMemberId(@RequestParam Long memberId) {
        ResponseResult result = new ResponseResult();
        if (memberId == null) {
            return result.failed("要查询的购物车id为空");
        }

        List<ShoppingCartInfo> cartList = this.shoppingCartService.getCartFromRedis(memberId);
        if (CollectionUtils.isEmpty(cartList)) {
            cartList = this.shoppingCartService.findCartByMemberId(memberId);
            if (CollectionUtils.isNotEmpty(cartList)) {
                this.shoppingCartService.addCartToRedis(memberId,cartList);
            }
        }
        return CollectionUtils.isEmpty(cartList) ? result.success("当前用户还没有添加商品到购物车") :
                result.success("查询成功",cartList);
    }
}
