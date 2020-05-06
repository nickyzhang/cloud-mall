package com.cloud.api;

import com.cloud.common.core.bean.ResponseResult;
import com.cloud.vo.user.UserDetailVO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "/catalogcloud-mall-user")
public interface UserDetailsService {

    @RequestMapping(value = "/user/detail/add", method = RequestMethod.POST)
    public ResponseResult save(@RequestBody UserDetailVO userDetailVO);

    @RequestMapping(value = "/user/detail/update", method = RequestMethod.POST)
    public ResponseResult update(@RequestBody UserDetailVO userDetailVO);

    @RequestMapping(value = "/user/detail/delete/{id}", method = RequestMethod.POST)
    public ResponseResult delete(@PathVariable("id") Long id);

    @RequestMapping(value = "/user/detail/findByUserId", method = RequestMethod.GET)
    public ResponseResult findByUserId(@RequestParam("userId") Long userId);

    @RequestMapping(value = "/user/detail/find/{id}", method = RequestMethod.GET)
    public ResponseResult find(@PathVariable("id") Long id);

    @RequestMapping(value = "/findAllUserId",method = RequestMethod.GET)
    public ResponseResult findAllUserId();

    @RequestMapping(value = "/findUserIdListByGender",method = RequestMethod.GET)
    public ResponseResult findUserIdListByGender(@RequestParam("gender") String gender);
}
