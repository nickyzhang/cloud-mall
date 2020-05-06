package com.cloud.web.interceptor;

import com.cloud.common.core.utils.CookieUtils;
import com.cloud.common.service.RestService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

@Slf4j
@Component
public class LoginInterceptor extends HandlerInterceptorAdapter {

    private static final String CLOUD_TOKEN = "CLOUD_TOKEN";

    @Autowired
    RestService restService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String token = "";
        // 从cookie中获取之前该用户的token,可能为null
        String oldToken = CookieUtils.getCookieValue(request,CLOUD_TOKEN, Boolean.FALSE);
        if (StringUtils.isNotBlank(oldToken)) {
            token = oldToken;
        }
        // 从当前请求域中获取该用户token,可能为null
        String newToken = request.getParameter("token");
        if (StringUtils.isNotBlank(newToken)) {
            token = newToken;
        }
        boolean should = shouldFilter(request);
        boolean checked = Boolean.FALSE;
        Map<String,Object> results = restService.getForObject("http://api.cloud.com/auth/verify?token="+token, Map.class);
        checked = (Boolean)results.get("status");

        if (StringUtils.isBlank(token) && !should) {
            log.info("[LoginInterceptor] 既没有token,也不需要拦截");
            return Boolean.TRUE;
        }

        // 校验不成功，且需要登录的，则需要重新登录
        if (!checked && should) {
            log.info("[LoginInterceptor] 校验没通过,但是需要拦截,进行登录");
            // 重定向到认证中心的登录页面
            String originURL = request.getRequestURL().toString();
            StringBuilder loginURL = new StringBuilder("http://passport.cloud.com:82/login");
            loginURL.append("?").append("returnUrl").append("=").append(originURL);
            response.sendRedirect(loginURL.toString());
            return Boolean.FALSE;
        }

        // 没有会话信息，直接返回
        if (!checked && !should) {
            return Boolean.FALSE;
        }

        // 校验成功，则需要将记录相关信息
        log.info("[LoginInterceptor] 校验成功，则放行该请求");
        Long userId = (Long)results.get("userId");
        request.setAttribute("userId",userId);

        // 验证通过，覆盖Cookie中的token，防止token在Cookie中过期(续期)
        // 如果是App没有cookie,则可以写入本地文件
        if (StringUtils.isNotBlank(token)) {
            CookieUtils.addCookie(request,response,".cloud.com",CLOUD_TOKEN,token,5 * 60 * 1);
        }
        return Boolean.TRUE;
    }

    private boolean shouldFilter(HttpServletRequest request) {
        String path = request.getServletPath();
        return StringUtils.isNotBlank(path) && (path.startsWith("/account") ||
                path.startsWith("/order") || path.startsWith("pay"));
    }
}
