package com.cloud.common.core.utils;

import com.cloud.common.core.constants.SymbolConstants;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLDecoder;
import java.net.URLEncoder;

/**
 * 操作Cookie的工具类
 * @author nickyzhang
 * @since 0.0.1
 */
public class CookieUtils {

    /**
     * 添加cookie
     * @param request
     * @param response
     * @param name
     * @param value
     * @param maxAge 存活时间
     */
    public static void addCookie(HttpServletRequest request, HttpServletResponse response, String name, String value, int maxAge) {
        try {
            setCookie(request,response,name,value,maxAge,Boolean.FALSE);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void addCookie(HttpServletRequest request, HttpServletResponse response, String domain, String name, String value, int maxAge) {
        try {
            setCookie(request,response,domain,name,value,maxAge,Boolean.FALSE);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 添加cookie
     * @param request
     * @param response
     * @param name
     * @param value
     * @param maxAge 存活时间
     * @param encode 是否使用UTF-8编码
     */
    public static void addCookie(HttpServletRequest request, HttpServletResponse response, String name, String value, int maxAge, boolean encode) {
        try {
            setCookie(request,response,name,value,maxAge,encode);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 添加cookie
     * @param request
     * @param response
     * @param name
     * @param value
     * @param encode 是否使用UTF-8编码
     */
    public static void addCookie(HttpServletRequest request, HttpServletResponse response, String name, String value, boolean encode) {
        try {
            setCookie(request,response,name,value,-1,encode);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 添加cookie
     * @param request
     * @param response
     * @param name
     * @param value
     */
    public static void addCookie(HttpServletRequest request, HttpServletResponse response, String name, String value) {
        try {
            setCookie(request,response,name,value,-1,Boolean.FALSE);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 根据cookie name获取cookie value,不进行解码
     * @param request
     * @param cookieName
     * @return
     */
    public static String getCookieValue(HttpServletRequest request, String cookieName) {
        return getCookieValue(request, cookieName, Boolean.FALSE);
    }

    /**
     * 根据cookie name获取cookie value
     * @param request
     * @param cookieName
     * @param isDecoder
     * @return
     */
    public static String getCookieValue(HttpServletRequest request, String cookieName, boolean isDecoder) {
        Cookie[] cookieList = request.getCookies();
        if (ArrayUtils.isEmpty(cookieList)) {
            return null;
        }

        try {
            for (Cookie cookie : cookieList) {
                if (!cookie.getName().equals(cookieName)) {
                    continue;
                }
                return isDecoder ? URLDecoder.decode(cookie.getValue(),"UTF-8") : cookie.getValue();
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 设置cookie
     * @param request
     * @param response
     * @param cookieName
     * @param cookieValue
     * @param maxAge
     * @param encode
     * @throws Exception
     */
    public static void setCookie(
            HttpServletRequest request, HttpServletResponse response,
            String cookieName, String cookieValue, int maxAge, boolean encode) throws Exception {
        if (StringUtils.isBlank(cookieValue)) {
            cookieValue = SymbolConstants.EMPTY_SYMBOL;
        }

        if (encode) {
            cookieValue = URLEncoder.encode(cookieValue, "UTF-8");
        }

        Cookie cookie = new Cookie(cookieName, cookieValue);
        if (maxAge > 0) {
            cookie.setMaxAge(maxAge);
        }

        if (request != null) {
            String domainName = getDomainName(request.getRequestURL().toString());
            if (!"localhost".equals(domainName)) {
                cookie.setDomain(domainName);
            }
        }
        cookie.setPath("/");
        response.addCookie(cookie);
    }

    public static void setCookie(
            HttpServletRequest request, HttpServletResponse response,String domain,
            String cookieName, String cookieValue, int maxAge, boolean encode) throws Exception {
        if (StringUtils.isBlank(cookieValue)) {
            cookieValue = SymbolConstants.EMPTY_SYMBOL;
        }

        if (encode) {
            cookieValue = URLEncoder.encode(cookieValue, "UTF-8");
        }

        Cookie cookie = new Cookie(cookieName, cookieValue);
        if (maxAge > 0) {
            cookie.setMaxAge(maxAge);
        }

        if (StringUtils.isNotBlank(domain)) {
            cookie.setDomain(domain);
        } else if (request != null) {
            String domainName = getDomainName(request.getRequestURL().toString());
            if (!"localhost".equals(domainName)) {
                cookie.setDomain(domainName);
            }
        }
        cookie.setPath("/");
        response.addCookie(cookie);
    }

    /**
     * 获取domain
     * @param url
     * @return
     * @throws MalformedURLException
     */
    public static String getDomainName(String url) throws MalformedURLException{
        URL netUrl = new URL(url);
        String host = netUrl.getHost();
        if(host.startsWith("www")){
            host = host.substring("www".length()+1);
        }
        return host;
    }

    /**
     * 移除cookie
     * @param request
     * @param response
     * @param cookieName
     */
    public static void removeCookie(HttpServletRequest request, HttpServletResponse response, String cookieName) {
        try {
            setCookie(request,response,cookieName,null,0,Boolean.FALSE);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
