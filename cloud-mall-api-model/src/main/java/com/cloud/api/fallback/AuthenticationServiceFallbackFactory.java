package com.cloud.api.fallback;

import com.cloud.api.AuthenticationService;
import feign.hystrix.FallbackFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@Component
public class AuthenticationServiceFallbackFactory implements FallbackFactory<AuthenticationService> {

    @Override
    public AuthenticationService create(Throwable throwable) {
        log.warn(String.format("校验请求token异常,开始回退"),throwable);
        return new AuthenticationService() {
            @Override
            public Map<String, Object> verify(String token) {
                Map<String,Object> results = new ConcurrentHashMap<>();
                results.put("status",Boolean.FALSE);
                return results;
            }
        };
    }
}
