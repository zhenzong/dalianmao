package me.dalianmao.util.web.interceptor;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.ArrayUtils;

/**
 * 处理cookie的interceptor
 *
 * @author xiezhenzong
 *
 */
public class CookieInterceptor extends HandlerInterceptorAdapter {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        Cookie[] cookies = request.getCookies();
        if (ArrayUtils.isEmpty(cookies)) {
            return handleNoCookie();
        } else {
            return handleCookie(cookies, request, response);
        }
    }

    protected boolean handleNoCookie() {
        return true;
    }

    protected boolean handleCookie(Cookie[] cookies, HttpServletRequest request, HttpServletResponse response) {
        return true;
    }
}
