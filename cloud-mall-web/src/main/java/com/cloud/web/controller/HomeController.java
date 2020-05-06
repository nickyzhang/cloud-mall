package com.cloud.web.controller;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.cloud.common.core.bean.ResponseResult;
import com.cloud.common.core.enums.ResultCodeEnum;
import com.cloud.common.service.RestService;
import com.cloud.model.catalog.Brand;
import com.cloud.model.catalog.Category;
import com.cloud.vo.user.UserInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Controller;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class HomeController {

    @Autowired
    RestService restService;

    @GetMapping({"/","/index"})
    public ModelAndView home(HttpServletRequest request) {
        ModelAndView mv = new ModelAndView();

        MultiValueMap<String, Object> params = new LinkedMultiValueMap<>();
        params.add("phone","15711229620");
        params.add("password","123456");
        JSONObject userJson = this.restService.postForObject("http://api.cloud.com/user/user/findByPhone",
                JSONObject.class,params);
        TypeReference<ResponseResult<UserInfo>> userType = new TypeReference<ResponseResult<UserInfo>>(){};
        ResponseResult<UserInfo> userRessult = userJson.toJavaObject(userType);
        if (userRessult.getCode() == ResultCodeEnum.OK.getCode()) {
            UserInfo userInfo = userRessult.getData();
            mv.addObject("user",userInfo);
        }

        JSONObject categoryListJson = this.restService.postForObject
                ("http://api.cloud.com/catalog/catalog/cat/findAll",JSONObject.class,null);
        TypeReference<ResponseResult<List<Category>>> catListType = new TypeReference<ResponseResult<List<Category>>>(){};
        ResponseResult<List<Category>> categoryListResult = categoryListJson.toJavaObject(catListType);
        if (categoryListResult.getCode() == ResultCodeEnum.OK.getCode()) {
            List<Category> categoryList = categoryListResult.getData();
            mv.addObject("categoryList",categoryList);
        }

        JSONObject brandListJson = this.restService.postForObject
                ("http://api.cloud.com/catalog/brand/cat/findAll",JSONObject.class,null);
        TypeReference<ResponseResult<List<Brand>>> brandListType = new TypeReference<ResponseResult<List<Brand>>>(){};
        ResponseResult<List<Brand>> brandListResult = brandListJson.toJavaObject(brandListType);
        if (brandListResult.getCode() == ResultCodeEnum.OK.getCode()) {
            List<Brand> brandList = brandListResult.getData();
            mv.addObject("brandList",brandList);
        }
        mv.setViewName("home");
        return mv;
    }
}
