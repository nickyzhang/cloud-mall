package com.cloud.api;

import com.cloud.common.core.bean.ResponseResult;
import com.cloud.vo.user.SocialBindParam;
import com.cloud.vo.user.SocialUserVO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(value = "cloud-mall-user")
public interface SocialUserService {

    @RequestMapping(value = "/user/social/add", method = RequestMethod.POST)
    public ResponseResult save(@RequestBody SocialUserVO socialUserVO);

    @RequestMapping(value = "/user/social/update", method = RequestMethod.PUT)
    public ResponseResult update(@RequestBody SocialUserVO socialUserVO);

    @RequestMapping(value = "/user/social/delete/{id}", method = RequestMethod.DELETE)
    public ResponseResult delete(@PathVariable("id") Long id);

    @RequestMapping(value = "/user/social/deleteByUserId", method = RequestMethod.DELETE)
    public ResponseResult deleteByUserId(@RequestParam("userId") Long userId);

    @RequestMapping(value = "/user/social/findByUserId", method = RequestMethod.GET)
    public ResponseResult findByUserId(@RequestParam("userId") Long userId);

    @RequestMapping(value = "/user/social/findByOpenId", method = RequestMethod.GET)
    public ResponseResult findByOpenId(@RequestParam("openId") String openId);

    @RequestMapping(value = "/user/social/bind/qq", method = RequestMethod.POST)
    public ResponseResult qqBind(@RequestBody SocialBindParam socialBindParam);

    @RequestMapping(value = "/user/social/bind/wechat", method = RequestMethod.POST)
    public ResponseResult wechatBind(@RequestBody SocialBindParam socialBindParam);
}
