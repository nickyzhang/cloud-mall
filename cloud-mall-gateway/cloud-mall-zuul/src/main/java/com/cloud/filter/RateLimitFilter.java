package com.cloud.filter;

import com.cloud.config.ZuulServerConfig;
import com.google.common.util.concurrent.RateLimiter;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.constants.ZuulConstants;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.netflix.zuul.filters.support.FilterConstants;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

@Slf4j
@Component
public class RateLimitFilter extends ZuulFilter {

    RateLimiter RATE_LIMITER = RateLimiter.create(10);
    // permitsPerSecond: 流入速率
    // warmupPeriod: 从冷启动速率过度到平均速率需要花费的时间
    RateLimiter SMOOTH_WARM_RATE_LIMITER =
            RateLimiter.create(100,1000, TimeUnit.MILLISECONDS);

    @Autowired
    ZuulServerConfig zuulServerConfig;

    @Override
    public String filterType() {

        return FilterConstants.PRE_TYPE;
    }

    @Override
    public int filterOrder() {

        return FilterConstants.SERVLET_DETECTION_FILTER_ORDER - 1;
    }

    @Override
    public boolean shouldFilter() {
        RequestContext context = RequestContext.getCurrentContext();
        HttpServletRequest request = context.getRequest();
        String path = request.getServletPath();
        if (StringUtils.isBlank(path)) {
            return false;
        }
        for (String prefix : this.zuulServerConfig.getNeedLimitPathList()) {
            if (path.startsWith(prefix)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public Object run() throws ZuulException {
        RequestContext context = RequestContext.getCurrentContext();
        //每调用一次tryAcquire()方法，令牌数量减1
        if (!RATE_LIMITER.tryAcquire()) {
            log.error("请求被限流: {}", context.getRequest().getRequestURI());
            context.setSendZuulResponse(false);
            context.setResponseStatusCode(HttpStatus.TOO_MANY_REQUESTS.value());
            HttpServletResponse response = context.getResponse();
            response.setContentType("text/html;charset=UTF-8");
            context.setResponseBody("服务器繁忙，请稍后再试!");
        }
        return null;
    }
}
