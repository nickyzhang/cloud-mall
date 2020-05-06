package com.cloud.api;

import com.cloud.api.fallback.AuthenticationServiceFallbackFactory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import java.util.Map;

@FeignClient(name = "/catalogcloud-mall-auth", fallbackFactory = AuthenticationServiceFallbackFactory.class)
public interface AuthenticationService {

    @RequestMapping(value = "/catalog/verify", method = RequestMethod.GET)
    public Map<String,Object> verify(@RequestParam("token")String token);
}