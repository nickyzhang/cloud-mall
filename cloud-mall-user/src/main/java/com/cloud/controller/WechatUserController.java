package com.cloud.controller;

import com.cloud.common.core.bean.SocialToken;
import com.cloud.common.core.bean.WechatUserInfo;
import com.cloud.common.core.enums.ResultCodeEnum;
import com.cloud.common.core.service.GenIdService;
import com.cloud.common.core.utils.ServletUtils;
import com.cloud.model.user.SocialUser;
import com.cloud.model.user.User;
import com.cloud.model.user.UserDetail;
import com.cloud.config.WechatConfig;
import com.cloud.constants.SocialConstants;
import com.cloud.exception.UserBizException;
import com.cloud.service.SocialUserService;
import com.cloud.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.time.LocalDateTime;
import java.util.UUID;

@Controller
@RequestMapping("/wechat")
public class WechatUserController {

    @Autowired
    WechatConfig wechatConfig;

    @Autowired
    @Qualifier("wechatService")
    private SocialUserService wechatService;

    @Autowired
    private UserService userService;

    @Autowired
    GenIdService idGenService;


    @RequestMapping(value="/login",method = RequestMethod.GET)
    public RedirectView login() {
        String state = UUID.randomUUID().toString();
        String qrcodeUrl = String.format(SocialConstants.WECHAT_AUTHORIZATION_URL,
                this.wechatConfig.getAppId(),this.wechatConfig.getCallbackUrl(),state);
        return new RedirectView(qrcodeUrl);
    }

    @RequestMapping(value = "/callback", params = {"code", "state"})
    public String callback(HttpServletRequest request, String code, String state, RedirectAttributes model) {
        if (StringUtils.isBlank(code)) {
            throw new IllegalArgumentException("授权码code不能为空");
        }

        if (StringUtils.isBlank(state)) {
            throw new IllegalArgumentException("state不能为空");
        }

        String accessTokenUrl = String.format(SocialConstants.WECHAT_ACCESS_TOKEN_URL,
                this.wechatConfig.getAppId(),this.wechatConfig.getSecret(), code);

        SocialToken socialToken = this.wechatService.getSocialToken(accessTokenUrl);
        if (socialToken == null || StringUtils.isBlank(socialToken.getAccessToken())) {
            throw new UserBizException(ResultCodeEnum.SOCIAL_GET_ACCESS_TOKEN_FAIDED,"Wechat");
        }

        String openId = socialToken.getOpenId();
        if (StringUtils.isBlank(openId)) {
            throw new UserBizException(ResultCodeEnum.SOCIAL_GET_OPEN_ID_FAIDED,"Wechat");
        }

        // 如果已经绑定过一定存在这个用户
        SocialUser socialUser = this.wechatService.findSocialUserByOpenId(openId);
        // 如果用户不存在，则表示没有绑定过，应该跳到绑定页面
        String redirectUrl = request.getParameter("redirectUrl");
        String targetUrl = StringUtils.isNotBlank(redirectUrl) ? redirectUrl : SocialConstants.DEFAULT_REDIRECT_URL;

        if (socialUser == null) {
            request.getSession().setAttribute("openId",openId);
            model.addAttribute("targetUrl",targetUrl);
            model.addAttribute("accessToken",socialToken.getAccessToken());
            return "redirect:/user/bind";
        }

        User user = this.userService.find(socialUser.getUserId());
        if (user != null) {
            request.getSession().setAttribute("user",user);
        } else {
            request.getSession().setAttribute("openId",openId);
            model.addAttribute("targetUrl",targetUrl);
            model.addAttribute("accessToken",socialToken.getAccessToken());
            return "redirect:/user/bind";
        }

        return "redirect:"+targetUrl;
    }

    @RequestMapping("/dobind")
    public String bind(HttpServletRequest request, HttpServletResponse response, RedirectAttributes model) {
        String openId = (String)request.getSession().getAttribute("openId");
        if (openId == null){
            throw new IllegalArgumentException("绑定的用户不存在");
        }

        String phone = request.getParameter("phone");
        if (StringUtils.isBlank(phone)) {
            throw new IllegalArgumentException("绑定的手机号为空");
        }

        String token = request.getParameter("accessToken");
        if (openId == null){
            throw new IllegalArgumentException("绑定时获取token失败");
        }

        String targetUrl = request.getParameter("targetUrl");
        if (StringUtils.isBlank(targetUrl)) {
            targetUrl = SocialConstants.DEFAULT_REDIRECT_URL;
        }

        String getUserInfoUrl = String.format(SocialConstants.WECHAT_USERINFO_URL,token,openId);
        WechatUserInfo userInfo = (WechatUserInfo)this.wechatService.getUserInfo(getUserInfoUrl);

        User user = new User();
        user.setUserId(this.idGenService.genId());
        user.setPhone(phone);
        user.setCreateDate(LocalDateTime.now());
        user.setUpdateDate(LocalDateTime.now());
        user.setNickname(userInfo.getNickname());
        user.setAvator(userInfo.getAvator());

        SocialUser socialUser = new SocialUser();
        socialUser.setId(this.idGenService.genId());
        socialUser.setUserId(user.getUserId());
        socialUser.setProviderId("Wechat");
        socialUser.setOpenId(userInfo.getUnionid());
        socialUser.setCreateDate(LocalDateTime.now());
        socialUser.setUpdateDate(LocalDateTime.now());

        UserDetail userDetail = new UserDetail();
        userDetail.setId(this.idGenService.genId());
        userDetail.setUserId(user.getUserId());
        userDetail.setGender(userInfo.getGender());
        userDetail.setRegisterIp(ServletUtils.getIpAddress(request));
        userDetail.setRegisterTime(user.getCreateDate());
        userDetail.setLastLoginIp(ServletUtils.getIpAddress(request));
        userDetail.setLastLoginTime(user.getCreateDate());
        userDetail.setCreateDate(LocalDateTime.now());
        userDetail.setUpdateDate(LocalDateTime.now());

        // 绑定
        this.wechatService.bindSocialUser(user,socialUser,userDetail);

        request.getSession().setAttribute("user",user);
        return "redirect:"+targetUrl;
    }
}
