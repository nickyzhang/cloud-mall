package com.cloud.web.controller;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.cloud.common.core.bean.ResponseResult;
import com.cloud.common.core.enums.ResultCodeEnum;
import com.cloud.common.core.utils.BeanUtils;
import com.cloud.common.core.utils.CookieUtils;
import com.cloud.common.core.utils.ServletUtils;
import com.cloud.common.service.RestService;
import com.cloud.model.promotion.Activity;
import com.cloud.model.promotion.CouponTemplate;
import com.cloud.po.cart.ShoppingCartParam;
import com.cloud.vo.cart.ShoppingCartInfo;
import com.cloud.vo.catalog.SkuVo;
import com.cloud.vo.user.UserInfo;
import com.cloud.vo.user.UserVO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.util.List;

@Slf4j
@Controller
public class ShoppingCartController {

    private static final String IDU_SIN = "_idusin";

    @Autowired
    RestService restService;



    @RequestMapping(value = "/cart/add",method = RequestMethod.POST)
    @ResponseBody
    public ResponseResult addToCart(HttpServletRequest request,
                                    HttpServletResponse response,
                                    @RequestParam("skuId") Long skuId,
                                    @RequestParam("count") int count) {
        Long memberId = (Long)request.getAttribute("userId");
        log.info("Member id:{}",memberId);
        MultiValueMap<String, Object> params = new LinkedMultiValueMap<>();
        params.add("skuId",skuId);
        JSONObject skuJSON = this.restService.getForObject("http://api.cloud.com/catalog/sku/list/"+skuId,JSONObject.class);
        TypeReference<ResponseResult<SkuVo>> skuType= new TypeReference<ResponseResult<SkuVo>>(){};
        ResponseResult<SkuVo> skuResult = skuJSON.toJavaObject(skuType);
        if (skuResult.getCode() != ResultCodeEnum.OK.getCode()) {
            return skuResult;
        }
        SkuVo skuVo = skuResult.getData();
        ShoppingCartParam shoppingCartParam = new ShoppingCartParam();
        shoppingCartParam.setMemberId(memberId);
        shoppingCartParam.setSkuId(skuVo.getId());
        shoppingCartParam.setSkuName(skuVo.getName());
        shoppingCartParam.setSkuPicture(skuVo.getImageUrl());
        shoppingCartParam.setUnitPrice(BigDecimal.valueOf(skuVo.getSalePrice()/100));
        shoppingCartParam.setBeforeUnitPrice(BigDecimal.valueOf(skuVo.getSalePrice()/100));
        shoppingCartParam.setPriceChange(shoppingCartParam.getBeforeUnitPrice().subtract(shoppingCartParam.getUnitPrice()));
        shoppingCartParam.setQuantity(count);
        shoppingCartParam.setTotalPrice(BigDecimal.valueOf(skuVo.getSalePrice()/100).multiply(BigDecimal.valueOf(count)));


        ShoppingCartInfo cartInfo = BeanUtils.copy(shoppingCartParam,ShoppingCartInfo.class);

        // 如果没有登录，则创建一个随机码写入cookie，并且和具体的IP地址绑定，标识客户端存入到Redis
        if (memberId == null) {
            MultiValueMap<String, Object> findActParams = new LinkedMultiValueMap<>();
            findActParams.add("skuId",skuId);
            JSONObject actJSON = this.restService.getForObject(
                    "http://api.cloud.com/promotion/act/findBySkuId", JSONObject.class,findActParams);
            TypeReference<ResponseResult<List<Activity>>> actType = new TypeReference<ResponseResult<List<Activity>>>(){};
            ResponseResult<List<Activity>> actListResult = actJSON.toJavaObject(actType);
            if (actListResult.getCode() == ResultCodeEnum.OK.getCode()) {
                List<Activity> activityList = actListResult.getData();
                if (!CollectionUtils.isEmpty(activityList)) {
                    cartInfo.setActivityList(activityList);
                }
            }
            // 这时候可以将购物车信息写入Cookie
            int random = RandomUtils.nextInt(1000000,99999999);
            CookieUtils.addCookie(request,response,".cloud.com",IDU_SIN,String.valueOf(random),
                    60 * 60 * 24 * 30);

            String ipAddress = ServletUtils.getIpAddress(request);
            String key = ipAddress+":"+random;
            log.info("用户未登录,存储到Redis, KEY: {}",key);
            this.redisService.hset(key,String.valueOf(shoppingCartParam.getSkuId()),shoppingCartParam);
            return new ResponseResult().success("保存成功");
        } else { // 如果用户已经登录,则需要保存到数据库，并且需要合并之前的
            log.info("用户已登录,需要存储到数据库");
            MultiValueMap<String, Object> addToCartParam = new LinkedMultiValueMap<>();
            addToCartParam.add("shoppingCartParam",shoppingCartParam);

            JSONObject addToCartJson = this.restService.postForObject
                    ("http://api.cloud.com/cart/cart/add",JSONObject.class,addToCartParam);
            TypeReference<ResponseResult> addToCartType= new TypeReference<ResponseResult>(){};
            return addToCartJson.toJavaObject(addToCartType);
        }
    }

    /**
     * 不应该获取优惠券模板信息，要用户点击优惠券的时候才展示出来
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/cart/show",method = RequestMethod.GET)
    public ModelAndView showMemberCartInfo(HttpServletRequest request){
        ModelAndView mv = new ModelAndView("showCart");
        Long userId = (Long)request.getAttribute("userId");
        if (userId == null) {
            return mv;
        }
        MultiValueMap<String, Object> params = new LinkedMultiValueMap<>();
        params.add("userId",userId);
        JSONObject cartJSON = this.restService.getForObject("http://api.cloud.com/cart/cart/findByMemberId",JSONObject.class,params);
        TypeReference<ResponseResult<List<ShoppingCartInfo>>> cartInfoType= new TypeReference<ResponseResult<List<ShoppingCartInfo>>>(){};
        ResponseResult<List<ShoppingCartInfo>> memberCartResult = cartJSON.toJavaObject(cartInfoType);
        if (memberCartResult.getCode() != ResultCodeEnum.OK.getCode()){
            return mv;
        }
        List<ShoppingCartInfo> memberCartList = memberCartResult.getData();
        if (CollectionUtils.isEmpty(memberCartList)) {
            return mv;
        }
        mv.addObject("cartList",memberCartList);
        return mv;
    }

    /**
     * 获取优惠券模板信息
     * @param skuId
     * @return
     */
    @RequestMapping(value = "/getCouponTemplateList",method = RequestMethod.GET)
    @ResponseBody
    public List<CouponTemplate> getCouponTemplateList(@RequestParam("skuId") Long skuId) {
        MultiValueMap<String, Object> findCouponTemplateParams = new LinkedMultiValueMap<>();
        findCouponTemplateParams.add("skuId",skuId);
        JSONObject templateJSON = this.restService.getForObject(
                "http://api.cloud.com/promotion/couponTemplate/findBySkuId", JSONObject.class,findCouponTemplateParams);
        TypeReference<ResponseResult<List<Activity>>> templateType = new TypeReference<ResponseResult<List<Activity>>>(){};
        ResponseResult<List<CouponTemplate>> templateListResult = templateJSON.toJavaObject(templateType);
        if (templateListResult.getCode() != ResultCodeEnum.OK.getCode()) {
            return null;
        }
        return templateListResult.getData();
    }


    @RequestMapping(value = "/updateCheckedStatus",method = RequestMethod.POST)
    @ResponseBody
    public ResponseResult updateCheckedStatus(@RequestParam("skuId") Long skuId,
                                              @RequestParam("checked") boolean checked,
                                              HttpServletRequest request) {
        return null;
    }

    @RequestMapping(value = "/changeNum",method = RequestMethod.POST)
    @ResponseBody
    public ResponseResult changeNum(@RequestParam("skuId") Long skuId,
                                    @RequestParam("count") int count,
                                    HttpServletRequest request) {
        return null;
    }

    @RequestMapping(value = "/removeItemFromCart",method = RequestMethod.POST)
    @ResponseBody
    public ResponseResult removeItemFromCart(@RequestParam("skuId") Long skuId) {
        return null;
    }



}
