package com.cloud.web.controller;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.cloud.common.core.bean.ResponseResult;
import com.cloud.common.core.enums.ResultCodeEnum;
import com.cloud.common.core.utils.CookieUtils;
import com.cloud.common.service.RestService;
import com.cloud.vo.user.UserInfo;
import com.cloud.vo.user.UserVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Slf4j
@Controller
public class PassportController {
    @Autowired
    RestService restService;

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String login(@RequestParam("returnUrl") String returnUrl, ModelMap modelMap) {
        modelMap.addAttribute("returnUrl",returnUrl);
        return "login";
    }

    @RequestMapping(value = "/signin", method = RequestMethod.POST)
    public String signIn(@RequestParam("account") String account, @RequestParam("password")
            String password, @RequestParam("returnUrl")String returnUrl, ModelMap model) {
        MultiValueMap<String,Object> params = new LinkedMultiValueMap<>();
        params.add("account",account);
        params.add("password",password);

        JSONObject object = this.restService.postForObject("http://api.cloud.com/user/user/auth/login",JSONObject.class,params);
        TypeReference<ResponseResult<UserInfo>> typeReference = new TypeReference<ResponseResult<UserInfo>>(){};
        ResponseResult<UserInfo> result = object.toJavaObject(typeReference);
        if (result.getCode() != ResultCodeEnum.OK.getCode()) {
            model.addAttribute("errorMsg",result.getMsg());
            return "redirect:/login?returnUrl="+returnUrl;
        }

        UserInfo user = result.getData();
        String token = buildToken(user);
        StringBuilder returnUrlBuilder = new StringBuilder(returnUrl);
        int index = returnUrlBuilder.indexOf("?");
        if (index == -1 ){
            returnUrlBuilder.append("?token=").append(token);
        } else {
            returnUrlBuilder.append("&token=").append(token);
        }

        log.info("[PassportController -> signin()] 重定向到: "+returnUrlBuilder.toString());
        return "redirect:"+returnUrlBuilder.toString();
    }

    @RequestMapping(value = "/smsLogin", method = RequestMethod.POST)
    public String smsLogin(@RequestParam("phone") String phone, @RequestParam("code")
            String code, @RequestParam("returnUrl")String returnUrl, ModelMap model) {

        MultiValueMap<String,Object> params = new LinkedMultiValueMap<>();
        params.add("phone",phone);
        params.add("code",code);
        JSONObject object = this.restService.postForObject
                ("http://api.cloud.com/user/user/auth/smsLogin",JSONObject.class,params);
        TypeReference<ResponseResult<UserInfo>> typeReference = new TypeReference<ResponseResult<UserInfo>>(){};
        ResponseResult<UserInfo> result = object.toJavaObject(typeReference);
        if (result.getCode() != ResultCodeEnum.OK.getCode()) {
            String errorMsg = result.getMsg();
            model.addAttribute("errorMsg",errorMsg);
            return "redirect:/login?returnUrl="+returnUrl;
        }

        UserInfo user = result.getData();
        String token = buildToken(user);
        StringBuilder returnUrlBuilder = new StringBuilder(returnUrl);
        int index = returnUrlBuilder.indexOf("?");
        if (index == -1 ){
            returnUrlBuilder.append("?token=").append(token);
        } else {
            returnUrlBuilder.append("&token=").append(token);
        }
        log.info("[PassportController -> smsLogin()] 重定向到: "+returnUrlBuilder.toString());
        return "redirect:"+returnUrlBuilder.toString();
    }

    @RequestMapping(value = "/mobile/login", method = RequestMethod.POST)
    @ResponseBody
    public Map<String,Object> mobileLogin(@RequestParam("account") String account,
                                          @RequestParam("password") String password,
                                          @RequestParam("returnUrl") String returnUrl) {
        Map<String,Object> results = new HashMap<>();

        MultiValueMap<String,Object> params = new LinkedMultiValueMap<>();
        params.add("account",account);
        params.add("password",password);
        JSONObject object = this.restService.postForObject
                ("http://api.cloud.com/user/user/auth/login",JSONObject.class,params);
        TypeReference<ResponseResult<UserInfo>> typeReference = new TypeReference<ResponseResult<UserInfo>>(){};
        ResponseResult<UserInfo> result = object.toJavaObject(typeReference);
        if (result.getCode() != ResultCodeEnum.OK.getCode()) {
            results.put("error",result.getMsg());
            results.put("code", result.getCode());
            return results;
        }

        UserInfo user = result.getData();
        String token = buildToken(user);
        results.put("token",token);
        results.put("code",result.getCode());
        StringBuilder returnUrlBuilder = new StringBuilder(returnUrl);
        int index = returnUrlBuilder.indexOf("?");
        if (index == -1 ){
            returnUrlBuilder.append("?token=").append(token);
        } else {
            returnUrlBuilder.append("&token=").append(token);
        }
        results.put("returnUrl",returnUrlBuilder.toString());
        return results;
    }

    @GetMapping("/register")
    public String register(@RequestParam("returnUrl") String returnUrl, ModelMap modelMap) {
        modelMap.addAttribute("returnUrl",returnUrl);
        return "register";
    }

    @PostMapping("/signup")
    public ModelAndView signUp(UserVO userVO) {
        MultiValueMap<String,Object> signUpParams = new LinkedMultiValueMap<>();
        signUpParams.add("userVO",userVO);

        JSONObject singupObject = this.restService.postForObject
                ("http://api.cloud.com/user/user/auth/register",JSONObject.class,signUpParams);
        TypeReference<ResponseResult<UserInfo>> typeReference = new TypeReference<ResponseResult<UserInfo>>(){};
        ResponseResult<UserInfo> result = singupObject.toJavaObject(typeReference);

        ModelAndView mv = null;
        // 注册成功，跳转首页
        if (result.getCode() == ResultCodeEnum.OK.getCode()) {
            MultiValueMap<String,Object> userParams = new LinkedMultiValueMap<>();
            userParams.add("phone",userVO.getPhone());
            userParams.add("password",userVO.getPassword());
            JSONObject userObject = this.restService.postForObject
                    ("http://api.cloud.com/user/user/findByPhone",JSONObject.class,userParams);
            ResponseResult<UserInfo> userResult = singupObject.toJavaObject(typeReference);
            if (userResult.getCode() == ResultCodeEnum.OK.getCode()) {
                UserInfo userDTO = userResult.getData();
                String token = buildToken(userDTO);
                mv = new ModelAndView(new RedirectView("http://www.cloud.com?token="+token));
                return mv;
            }
            log.error("[PassportController -> signUp()] 注册成功，却无法获取用户信息，可能是网络故障");
        }
        mv = new ModelAndView(new RedirectView("/register"));
        mv.addObject("errorMsg","登陆异常");
        return mv;
    }

    /**
     * 根据用户信息创建token
     * @param user
     * @return
     */
    private String buildToken(UserInfo user) {
        StringBuilder token = new StringBuilder();
        token.append("User").append(":");
        token.append(String.valueOf(user.getUserId())).append(":");
        token.append(UUID.randomUUID().toString().trim().replaceAll("-", ""));
        log.info("[PassportController -> buildToken] 创建新的token: {}",token.toString());
        MultiValueMap<String,Object> params = new LinkedMultiValueMap<>();
        params.add("token",token.toString());
        params.add("userId",user.getUserId());
        ResponseResult result = this.restService.postForObject("http://api.cloud.com/user/user/auth/setUserToken",ResponseResult.class,params);
        if (result.getCode() != ResultCodeEnum.OK.getCode()) {
            return null;
        }
        return token.toString();
    }

    @PostMapping(value = "/send/smsCode")
    @ResponseBody
    public ResponseResult sendSmsCode(@RequestParam("phone") String phone) {
        MultiValueMap<String,Object> params = new LinkedMultiValueMap<>();
        params.add("phone",phone);

        return this.restService.postForObject("http://api.cloud.com/user/user/auth/sendSmsCode",ResponseResult.class,params);
    }

    @PostMapping(value = "/send/mailCode")
    @ResponseBody
    public ResponseResult sendMailCode(@RequestParam("email") String email) {

        MultiValueMap<String,Object> params = new LinkedMultiValueMap<>();
        params.add("email",email);

        return this.restService.postForObject("http://api.cloud.com/user/user/auth/sendMailCode",ResponseResult.class,params);
    }

    /**
     * 注销
     * @param token
     * @param request
     * @param response
     */
    @PostMapping("/logout")
    public void logout(@CookieValue("CLOUD_TOKEN") String token, HttpServletRequest request, HttpServletResponse response) {
        CookieUtils.removeCookie(request,response,token);
        MultiValueMap<String,Object> params = new LinkedMultiValueMap<>();
        params.add("token",token);
        this.restService.postForObject("http://api.cloud.com/user/user/auth/removeToken",ResponseResult.class,params);
    }
}
