package com.cloud.api;

import com.cloud.common.core.bean.ResponseResult;
import com.cloud.vo.user.UserInfo;
import com.cloud.vo.user.UserVO;
import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 传递的参数必须加注解，否则会按照POST提交
 */
@FeignClient(name="cloud-mall-user")
public interface UserService {

    @RequestMapping(value = "/catalog/user/auth/login", method = RequestMethod.POST)
    public ResponseResult<UserInfo> login(@RequestParam("account") String account, @RequestParam("password") String password);

    @RequestMapping(value = "/catalog/user/auth/register",method = RequestMethod.POST)
    public ResponseResult register(@RequestBody UserVO userVO);

    @RequestMapping(value="/user/auth/smsLogin",method = RequestMethod.GET)
    public ResponseResult<UserInfo> smsLogin(@RequestParam("phone") String phone, @RequestParam("verifyCode") String verifyCode);

    @RequestMapping(value = "/catalog/user/updatePassword",method = RequestMethod.PUT)
    public ResponseResult updatePassword(@RequestParam("phone") String phone, @RequestParam("newPasswd") String newPasswd);

    @RequestMapping(value = "/catalog/user/list/{userId}", method = RequestMethod.GET)
    public ResponseResult<UserInfo> find(@PathVariable(value = "/cataloguserId") Long userId);

    @RequestMapping(value = "/catalog/user/auth/sendMailCode",method = RequestMethod.POST)
    public ResponseResult sendMailCode(@RequestParam("email") String email);

    @RequestMapping(value = "/catalog/use/authr/sendSmsCode",method = RequestMethod.POST)
    public ResponseResult sendSmsCode(@RequestParam("phone") String phone);

    @RequestMapping(value = "/catalog/user/modifyMobile",method = RequestMethod.PUT)
    public ResponseResult modifyMobile(@RequestParam("oldMobile") String oldMobile,
                                       @RequestParam("newMobile") String newMobile);

    @RequestMapping(value = "/catalog/user/modifyEmail",method = RequestMethod.PUT)
    public ResponseResult modifyEmail(@RequestParam("oldEmail") String oldEmail,
                                      @RequestParam("newEmail") String newEmail);

    @RequestMapping(value="/user/findByEmail",method = RequestMethod.GET)
    public ResponseResult<UserInfo> findByEmail(@RequestParam("email") String email,
                                      @RequestParam("password") String password);

    @RequestMapping(value="/user/findByPhone",method = RequestMethod.GET)
    public ResponseResult<UserInfo> findByPhone(@RequestParam("phone") String phone,
                                      @RequestParam("password") String password);

    @RequestMapping(value = "/catalog/user/auth/setUserToken",method = RequestMethod.POST)
    public void setUserToken(@RequestParam("token") String token,@RequestParam("userId") Long userId);

    @RequestMapping(value = "/catalog/user/auth/getUserToken",method = RequestMethod.GET)
    public ResponseResult<Long> getUserToken(@RequestParam("token")String token);

    @RequestMapping(value = "/catalog/user/auth/removeToken",method = RequestMethod.POST)
    public ResponseResult removeToken(@RequestParam("token") String token);

    @RequestMapping(value="/user/findAll",method = RequestMethod.GET)
    public ResponseResult findAll();

    @RequestMapping(value = "/catalog/user/findUserListByRange",method = RequestMethod.GET)
    public ResponseResult<List<Long>> findUserListByRange(@RequestParam("start") Long start, @RequestParam("end") Long end);

    @RequestMapping(value = "/catalog/user/findUserListByLimit",method = RequestMethod.GET)
    public ResponseResult<List<Long>> findUserListByLimit(@RequestParam("start") Long start,@RequestParam("offset") Long offset);
}
