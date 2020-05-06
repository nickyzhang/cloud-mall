package com.cloud.filter;

import com.cloud.api.AuthenticationService;
import com.cloud.api.UserService;
import com.cloud.config.ZuulServerConfig;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.netflix.zuul.filters.support.FilterConstants;
import org.springframework.stereotype.Component;
import javax.servlet.http.HttpServletRequest;
import java.util.Map;
@Slf4j
//@Component
public class AccessFilter extends ZuulFilter {
    
    private static final String CLOUD_TOKEN = "cloud_token";

    @Autowired
    AuthenticationService authenticationService;

    @Autowired
    UserService userService;

    @Autowired
    ZuulServerConfig zuulServerConfig;

    @Override
    public String filterType() {
        return FilterConstants.PRE_TYPE;
    }

    @Override
    public int filterOrder() {
        return 0;
    }

    @Override
    public boolean shouldFilter() {
        // 只要涉及认证相关的行为，比如注册、登录、发送验证码或者设置token或者验证用户都不需要被拦截,无需登录
        String path = RequestContext.getCurrentContext().getRequest().getServletPath();
        boolean should = path.contains("/auth");
        if (should) {
            log.info("[AccessFilter -> shouldFilter()] 当前路径{}不该被拦截",path);
        }
        return !should;
    }

    @Override
    public Object run() throws ZuulException {
        RequestContext ctx = RequestContext.getCurrentContext();
        HttpServletRequest request = ctx.getRequest();

        log.info("[AccessFilter -> run()] 当前的请求URL: {}",request.getRequestURL());
        // 从cookie中获取之前该用户的token,可能为null；如果是手机就从token中获取
        String token = request.getHeader("token");
        log.info("[AccessFilter -> run()] 从cookie中获取token:{}",token);

        // 是否应该拦截当前请求，如果是拦截在，则强制要求校验不通过就重定向到认证中心登录页
        boolean should = tokenFilter();
        if (StringUtils.isBlank(token) && !should) {
            log.info("[AccessFilter -> run()] 既没有token,也不需要拦截");
            return Boolean.TRUE;
        }

        Map<String,Object> results = this.authenticationService.verify(token);
        boolean checked = (Boolean)results.get("status");
        if (checked) {
            log.info("[AccessFilter -> run()] 校验成功，则放行");
            Long userId = (Long)results.get("userId");
            request.setAttribute("userId",userId);
            this.userService.setUserToken(token,userId);
            return null;
        } else {
            log.info("[AccessFilter -> run()] 校验失败");
            //解析token失败， 未登录拦截
            ctx.setSendZuulResponse(false);
            //返回状态码
            ctx.setResponseStatusCode(403);
        }
        return null;
    }

    /**
     * 是否应该拦截当前请求，如果是拦截在，则强制要求校验不通过就重定向到认证中心登录页
     * @return
     */
    private boolean tokenFilter() {
        RequestContext context = RequestContext.getCurrentContext();
        HttpServletRequest request = context.getRequest();
        String path = request.getServletPath();
        if (StringUtils.isBlank(path)) {
            return Boolean.FALSE;
        }
        for (String prefix : this.zuulServerConfig.getDisallowedPathsPrefix()) {
            if (path.startsWith(prefix)) {
                return Boolean.TRUE;
            }
        }
        return Boolean.FALSE;
    }
}
