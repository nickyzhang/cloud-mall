package com.cloud.web.controller;

import org.springframework.stereotype.Controller;

@Controller
public class QQSocialUserController {
//    @Autowired
//    @Qualifier("qqService")
//    private SocialUserService qqService;
//
//    @Autowired
//    private UserService userService;
//
//    @Autowired
//    GenIdService genIdService;
//
//    @Autowired
//    QQConfig qqConfig;
//
//    @RequestMapping(value="/login",method = RequestMethod.GET)
//    public String qqLogin(HttpServletRequest request) throws Exception {
//        String authorizeURL = String.format(SocialConstants.QQ_AUTHORIZATION_URL,this.qqConfig.getAppId(),
//                UUID.randomUUID(),this.qqConfig.getCallbackUrl());
//
//        return "redirect:"+authorizeURL;
//    }
//
//    @RequestMapping(value = "/callback", params = {"code", "state"})
//    public String callback(HttpServletRequest request, String code, String state, RedirectAttributes model) {
//        if (StringUtils.isBlank(code)) {
//            throw new IllegalArgumentException("授权码code不能为空");
//        }
//
//        if (StringUtils.isBlank(state)) {
//            throw new IllegalArgumentException("state不能为空");
//        }
//
//        String accessTokenUrl = String.format(SocialConstants.QQ_ACCESS_TOKEN_URL,
//                this.qqConfig.getAppId(),this.qqConfig.getSecret(), code, this.qqConfig.getCallbackUrl());
//
//        SocialToken socialToken = this.qqService.getSocialToken(accessTokenUrl);
//        if (socialToken == null || StringUtils.isBlank(socialToken.getAccessToken())) {
//            throw new UserBizException(ResultCodeEnum.SOCIAL_GET_ACCESS_TOKEN_FAIDED,"Wechat");
//        }
//
//        String openId = socialToken.getOpenId();
//        if (StringUtils.isBlank(openId)) {
//            throw new UserBizException(ResultCodeEnum.SOCIAL_GET_OPEN_ID_FAIDED,"Wechat");
//        }
//
//        // 如果已经绑定过一定存在这个用户
//        SocialUser socialUser = this.qqService.findSocialUserByOpenId(openId);
//        // 如果用户不存在，则表示没有绑定过，应该跳到绑定页面
//        String redirectUrl = request.getParameter("redirectUrl");
//        String targetUrl = StringUtils.isNotBlank(redirectUrl) ? redirectUrl : SocialConstants.DEFAULT_REDIRECT_URL;
//
//        if (socialUser == null) {
//            request.getSession().setAttribute("openId",openId);
//            model.addAttribute("targetUrl",targetUrl);
//            model.addAttribute("accessToken",socialToken.getAccessToken());
//            return "redirect:/user/bind";
//        }
//
//        User user = this.userService.find(socialUser.getUserId());
//        if (user != null) {
//            request.getSession().setAttribute("user",user);
//        } else {
//            request.getSession().setAttribute("openId",openId);
//            model.addAttribute("targetUrl",targetUrl);
//            model.addAttribute("accessToken",socialToken.getAccessToken());
//            return "redirect:/user/bind";
//        }
//
//        return "redirect:"+targetUrl;
//    }
//
//    @RequestMapping("/dobind")
//    public String bind(HttpServletRequest request, HttpServletResponse response, RedirectAttributes model) {
//
//        String openId = (String)request.getSession().getAttribute("openId");
//        if (openId == null){
//            throw new IllegalArgumentException("绑定的用户不存在");
//        }
//
//        String phone = request.getParameter("phone");
//        if (StringUtils.isBlank(phone)) {
//            throw new IllegalArgumentException("绑定的手机号为空");
//        }
//
//        String token = request.getParameter("accessToken");
//        if (openId == null){
//            throw new IllegalArgumentException("绑定时获取token失败");
//        }
//
//        String targetUrl = request.getParameter("targetUrl");
//        if (StringUtils.isBlank(targetUrl)) {
//            targetUrl = SocialConstants.DEFAULT_REDIRECT_URL;
//        }
//
//        String getUserInfoUrl = String.format(SocialConstants.QQ_USERINFO_URL,token,this.qqConfig.getAppId(), openId);
//        QQUserInfo userInfo = (QQUserInfo)this.qqService.getUserInfo(getUserInfoUrl);
//
//        User user = new User();
//        user.setUserId(this.genIdService.genId());
//        user.setPhone(phone);
//        user.setCreateDate(LocalDateTime.now());
//        user.setUpdateDate(LocalDateTime.now());
//        user.setNickname(userInfo.getNickname());
//        user.setAvator(userInfo.getAvator());
//
//        SocialUser socialUser = new SocialUser();
//        socialUser.setId(this.genIdService.genId());
//        socialUser.setUserId(user.getUserId());
//        socialUser.setProviderId("QQ");
//        socialUser.setOpenId(openId);
//        socialUser.setCreateDate(LocalDateTime.now());
//        socialUser.setUpdateDate(LocalDateTime.now());
//
//        UserDetail userDetail = new UserDetail();
//        userDetail.setId(this.genIdService.genId());
//        userDetail.setUserId(user.getUserId());
//        userDetail.setGender(userInfo.getGender());
//        userDetail.setRegisterIp(ServletUtils.getIpAddress(request));
//        userDetail.setRegisterTime(user.getCreateDate());
//        userDetail.setLastLoginIp(ServletUtils.getIpAddress(request));
//        userDetail.setLastLoginTime(user.getCreateDate());
//        userDetail.setCreateDate(LocalDateTime.now());
//        userDetail.setUpdateDate(LocalDateTime.now());
//
//        // 绑定
//        this.qqService.bindSocialUser(user,socialUser,userDetail);
//
//        request.getSession().setAttribute("user",user);
//        return "redirect:"+targetUrl;
//    }
}
