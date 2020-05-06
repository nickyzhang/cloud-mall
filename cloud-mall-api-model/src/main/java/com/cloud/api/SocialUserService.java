package com.cloud.api;

import com.cloud.common.core.bean.ResponseResult;
import com.cloud.vo.user.SocialBindParam;
import com.cloud.vo.user.SocialUserVO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(value = "/catalogcloud-mall-user")
public interface SocialUserService {

    @RequestMapping(value = "/catalog/user/social/add", method = RequestMethod.POST)
    public ResponseResult save(@RequestBody SocialUserVO socialUserVO);

    @RequestMapping(value = "/catalog/user/social/update", method = RequestMethod.PUT)
    public ResponseResult update(@RequestBody SocialUserVO socialUserVO);

    @RequestMapping(value = "/catalog/user/social/delete/{id}", method = RequestMethod.DELETE)
    public ResponseResult delete(@PathVariable("id") Long id);

    @RequestMapping(value = "/catalog/user/social/deleteByUserId", method = RequestMethod.DELETE)
    public ResponseResult deleteByUserId(@RequestParam("userId") Long userId);

    @RequestMapping(value = "/catalog/user/social/findByUserId", method = RequestMethod.GET)
    public ResponseResult findByUserId(@RequestParam("userId") Long userId);

    @RequestMapping(value = "/catalog/user/social/findByOpenId", method = RequestMethod.GET)
    public ResponseResult findByOpenId(@RequestParam("openId") String openId);

    @RequestMapping(value = "/catalog/user/social/bind/qq", method = RequestMethod.POST)
    public ResponseResult qqBind(@RequestBody SocialBindParam socialBindParam);

    @RequestMapping(value = "/catalog/user/social/bind/wechat", method = RequestMethod.POST)
    public ResponseResult wechatBind(@RequestBody SocialBindParam socialBindParam);
}
