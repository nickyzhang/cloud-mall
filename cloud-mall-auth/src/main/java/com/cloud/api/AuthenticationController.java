package com.cloud.api;

import com.cloud.common.core.bean.ResponseResult;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@Controller
public class AuthenticationController {

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/verify", method = RequestMethod.GET)
    @ResponseBody
    public Map<String,Object> verify(@RequestParam("token")String token) {
        Map<String,Object> map = new HashMap<>();
        if (StringUtils.isBlank(token)) {
            log.error("[AuthenticationController -> verify()] token参数为空");
            map.put("status",Boolean.FALSE);
            return map;
        }

        ResponseResult<Long> result = this.userService.getUserToken(token);
        Long userId = result.getData();
        // 如果会话到期，则Redis就没有对应的会话信息，需要重新登录
        if (userId == null) {
            log.error("[AuthenticationController -> verify()] 会话到期，没有会话数据");
            map.put("status",Boolean.FALSE);
            return map;
        }

        // 表示已经登录过了,而且还没有到期，则返回会话数据
        map.put("status",Boolean.TRUE);
        map.put("userId",userId);
        return map;
    }
}
