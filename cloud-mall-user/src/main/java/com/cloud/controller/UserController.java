package com.cloud.controller;

import com.cloud.common.core.ResponseResult;
import com.cloud.common.core.enums.ResultCodeEnum;
import com.cloud.common.core.exception.BizException;
import com.cloud.common.core.service.GenIdService;
import com.cloud.common.core.utils.ValidateUtils;
import com.cloud.exception.UserBizException;
import com.cloud.model.user.User;
import com.cloud.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    GenIdService genIdService;

    @Autowired
    UserService userService;

    @PostMapping(value="/register", params = {"username","password",
            "confirmPassword","phone","phoneCode","email","emailCode"})
    public ResponseResult register(HttpServletRequest request, String username, String password,
                                   String confirmPassword, String phone, String phoneCode, String email, String emailCode) {
        if (StringUtils.isBlank(username)) {
            throw new UserBizException(ResultCodeEnum.USER_EMPTY_NAME.getCode(), ResultCodeEnum.USER_EMPTY_NAME.getMessage());
        }

        if (StringUtils.isBlank(password)) {
            throw new UserBizException(ResultCodeEnum.USER_EMPTY_PASSWORD.getCode(), ResultCodeEnum.USER_EMPTY_PASSWORD.getMessage());
        }

        if (!confirmPassword.equals(password)) {
            throw new UserBizException(ResultCodeEnum.USER_DIFFERENT_PASSWORD.getCode(), ResultCodeEnum.USER_DIFFERENT_PASSWORD.getMessage());
        }

        if (StringUtils.isBlank(phone)) {
            throw new UserBizException(ResultCodeEnum.USER_EMPTY_PHONE.getCode(), ResultCodeEnum.USER_EMPTY_PHONE.getMessage());
        }


        if (StringUtils.isBlank(phoneCode)) {
            throw new UserBizException(ResultCodeEnum.USER_EMPTY_VERIFY_CODE.getCode(), ResultCodeEnum.USER_EMPTY_VERIFY_CODE.getMessage());
        }

        if (!this.userService.checkSmsCode(request,phoneCode)) {
            throw new UserBizException(ResultCodeEnum.USER_VERIFY_CODE_ERROR.getCode(), ResultCodeEnum.USER_VERIFY_CODE_ERROR.getMessage());
        }

        if (!StringUtils.isBlank(email) && !StringUtils.isBlank(emailCode) &&
                !this.userService.checkSmsCode(request,emailCode)) {
            throw new UserBizException(ResultCodeEnum.USER_VERIFY_CODE_ERROR.getCode(), ResultCodeEnum.USER_VERIFY_CODE_ERROR.getMessage());
        }

        User user = new User();
        user.setUserId(this.genIdService.genId());
        user.setUsername(username);
        user.setPassword(password);
        user.setPhone(phone);
        user.setEmail(email);
        user.setStatus(1);
        user.setNickname(phone);
        this.userService.save(user);
        ResponseResult result = new ResponseResult(200,"注册成功", user);
        request.getSession().setAttribute("user",user);
        return result;
    }

//    @PostMapping(value="/login")
//    @ResponseBody
//    public ResponseResult login(HttpServletRequest request) {
//        String account = request.getParameter("account");
//        if (StringUtils.isBlank(account)) {
//            throw new IllegalArgumentException("用户名不能为空");
//        }
//
//        String password = request.getParameter("password");
//        if (StringUtils.isBlank(password)) {
//            throw new IllegalArgumentException("密码不能为空");
//        }
//
//        User user = this.userService.findByUserName(account, password);
//        if (user == null) {
//            user = this.userService.findByPhone(account, password);
//            if (user == null) {
//                user = this.userService.findByEmail(account, password);
//            }
//        }
//
//        if (user == null) {
//            throw new UserBizException(ResultCodeEnum.USER_LOGIN_FAILED);
//        }
//
//        ResponseResult result = new ResponseResult();
//        result.setCode(ResultCodeEnum.OK.getCode());
//        result.setData(user);
//        request.getSession().setAttribute("user",user);
//        return result;
//    }

    @RequestMapping("/updatePassword")
    public ResponseResult updatePassword(@RequestParam String newPasswd, @RequestParam String authCode, HttpServletRequest request) {
        ResponseResult result = new ResponseResult(ResultCodeEnum.OK.getCode(),"更新密码成功");
        if (StringUtils.isBlank(newPasswd)) {
            throw new UserBizException(ResultCodeEnum.USER_EMPTY_NEW_PASSWORD.getCode(), ResultCodeEnum.USER_EMPTY_NEW_PASSWORD.getMessage());
        }

        if (StringUtils.isBlank(authCode)) {
            throw new UserBizException(ResultCodeEnum.USER_EMPTY_AUTH_CODE.getCode(), ResultCodeEnum.USER_EMPTY_AUTH_CODE.getMessage());
        }

        boolean success = this.userService.updatePassword(request,newPasswd,authCode);
        return success ? result.success("密码更新成功") : result.failed("密码更新失败");
    }


    @RequestMapping(value = "/{userId}", method = RequestMethod.GET)
    public ResponseResult find(@PathVariable("userId") Long userId, HttpServletRequest request) {
        User user = this.userService.find(userId);
        user.setPassword(null);
        ResponseResult result = new ResponseResult(ResultCodeEnum.OK.getCode(),null, user);
        request.getSession().setAttribute("user",user);
        return result;
    }

    @RequestMapping(value = "/info", method = RequestMethod.GET,params = {"username","password"})
    public ResponseResult findByUserName(HttpServletRequest request, String username, String password) {
        User user = this.userService.findByUserName(username, password);
        ResponseResult result = new ResponseResult();
        result.setCode(ResultCodeEnum.OK.getCode());
        if (user == null) {
            result.setMsg("用户名或者密码不正确");
        } else {
            result.setMsg("操作成功");
            request.getSession().setAttribute("user",user);
        }
        result.setData(user);
        return result;
    }

    @RequestMapping(value = "/info", method = RequestMethod.GET,params = {"email","password"})
    public ResponseResult findByEmail(HttpServletRequest request, String email, String password) {
        ResponseResult result = new ResponseResult();
        User user = this.userService.findByEmail(email, password);
        result.setCode(ResultCodeEnum.OK.getCode());
        if (user == null) {
            result.setMsg("邮件地址或者密码不正确");
        } else {
            result.setMsg("操作成功");
            request.getSession().setAttribute("user",user);
        }
        result.setData(user);
        return result;
    }

    @RequestMapping(value = "/info", method = RequestMethod.GET,params = {"phone","password"})
    public ResponseResult findByPhone(HttpServletRequest request, String phone, String password){
        User user = this.userService.findByPhone(phone, password);
        ResponseResult result = new ResponseResult();
        result.setCode(ResultCodeEnum.OK.getCode());
        if (user == null) {
            result.setMsg("手机号码或者密码不正确");
        } else {
            result.setMsg("操作成功");
            request.getSession().setAttribute("user",user);
        }
        result.setData(user);
        return result;
    }

    @PostMapping("/user/sendMail")
    public ResponseResult sendMail(
            HttpServletRequest request,
            @RequestParam("to") String to,
            @RequestParam("subject") String subject,
            @RequestParam("content") String content,
            @RequestParam("html") boolean html) {

        if (StringUtils.isBlank(to)) {
            throw new BizException(ResultCodeEnum.NOTIFY_MAIL_EMPTY_TO.getCode(),ResultCodeEnum.NOTIFY_MAIL_EMPTY_TO.getMessage());
        }

        if (!ValidateUtils.isEmail(to)) {
            throw new BizException(ResultCodeEnum.NOTIFY_MAIL_INVALID_ADDRESS.getCode(),ResultCodeEnum.NOTIFY_MAIL_INVALID_ADDRESS.getMessage());
        }

        if (StringUtils.isBlank(content)) {
            throw new UserBizException(ResultCodeEnum.NOTIFY_MAIL_EMPTY_CONTENT.getCode(), ResultCodeEnum.NOTIFY_MAIL_EMPTY_CONTENT.getMessage());
        }

        try {
            this.userService.sendMailCode(request, to, subject, content);
        } catch (Exception e) {
            throw new BizException(ResultCodeEnum.NOTIFY_MAIL_SEND_EXCEPTION.getCode(),ResultCodeEnum.NOTIFY_MAIL_SEND_EXCEPTION.getMessage());
        }
        return new ResponseResult().success("消息邮件成功");
    }

    @PostMapping("/user/sendSms")
    public ResponseResult sendSms(HttpServletRequest request, @RequestParam("phone") String phone, @RequestParam("data")String data) {
        if (StringUtils.isBlank(phone)) {
            throw new BizException(ResultCodeEnum.NOTIFY_SMS_EMPTY_PHONE.getCode(), ResultCodeEnum.NOTIFY_SMS_EMPTY_PHONE.getMessage());
        }

        if(!ValidateUtils.isPhone(phone)) {
            throw new BizException(ResultCodeEnum.NOTIFY_SMS_INVALID_PHONE_PATTERN.getCode(), ResultCodeEnum.NOTIFY_SMS_INVALID_PHONE_PATTERN.getMessage());
        }

        if (StringUtils.isBlank(data)) {
            throw new BizException(ResultCodeEnum.NOTIFY_SMS_EMPTY_MESSAGE.getCode(), ResultCodeEnum.NOTIFY_SMS_EMPTY_MESSAGE.getMessage());
        }

        ResponseResult result = new ResponseResult();
        try {
            this.userService.sendSmsCode(request,phone,data);
        } catch (Exception e) {
            throw new BizException(ResultCodeEnum.NOTIFY_SMS_SEND_EXCEPTION.getCode(),ResultCodeEnum.NOTIFY_SMS_SEND_EXCEPTION.getMessage());
        }
        return new ResponseResult().success("消息发送成功");
    }

//    @RequestMapping(value = "/smsLogin", method = RequestMethod.GET,params = {"phone","code"})
//    @ResponseBody
//    public ResponseResult smsLogin(HttpServletRequest request, String phone, String code) {
//
//        boolean success = this.userService.checkSmsCode(request, code);
//        ResponseResult result = new ResponseResult();
//        if (success) {
//            User user = this.userService.findByPhone(phone);
//            result.setCode(ResultCodeEnum.OK.getCode());
//            if (user == null) {
//                result.setMsg("手机号码或者验证码不正确");
//            } else {
//                result.setMsg("操作成功");
//            }
//            request.getSession().setAttribute("user",user);
//            result.setData(user);
//        } else {
//            result.setMsg("手机号码或者验证码不正确");
//        }
//        return result;
//    }
}
