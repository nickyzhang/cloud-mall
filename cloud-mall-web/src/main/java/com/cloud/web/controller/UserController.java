package com.cloud.web.controller;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.cloud.common.core.bean.ResponseResult;
import com.cloud.common.core.utils.CookieUtils;
import com.cloud.common.service.RestService;
import com.cloud.vo.user.UserInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Controller;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@Controller
public class UserController {

    @Autowired
    RestService restService;

    @GetMapping("/account/profile")
    public ModelAndView getUserInfo(HttpServletRequest request) {
        String token = CookieUtils.getCookieValue(request,"CLOUD_TOKEN");
        log.info("[UserController -> getUserInfo()] 获取用户token:{}",token);
        String returnUrl = request.getRequestURL().toString();
        ModelAndView mv = null;
        Long userId = (Long)request.getAttribute("userId");
        if (userId == null) {
            RedirectView redirectView = new RedirectView("http://passport.cloud.com:82/login?returnUrl="+returnUrl);
            mv = new ModelAndView(redirectView);
            return mv;
        } else {
            mv = new ModelAndView();
            MultiValueMap<String,String> headers = new LinkedMultiValueMap<>();
            headers.add("token",token);
            JSONObject object = this.restService.exchange("http://api.cloud.com/user/user/list/"+userId,HttpMethod.GET,JSONObject.class,null,headers);
            if (object != null) {
                TypeReference<ResponseResult<UserInfo>> type = new TypeReference<ResponseResult<UserInfo>>(){};
                ResponseResult<UserInfo> result = object.toJavaObject(type);
                UserInfo userInfo = result.getData();
                mv.addObject("user",userInfo);
            }
        }
        mv.setViewName("home");
        return mv;
    }

    @GetMapping("/member/myReturnGoodList")
    public ModelAndView getReturnGoodList(HttpServletRequest request) {
        return null;
    }

    @GetMapping("/member/messages")
    public ModelAndView getMyMessageList(HttpServletRequest request) {
        return null;
    }

    @GetMapping("/member/favorites")
    public ModelAndView getMyFavorites(HttpServletRequest request) {
        return null;
    }

    @GetMapping("/member/wishlist")
    public ModelAndView getMyWishList(HttpServletRequest request) {
        return null;
    }
}
