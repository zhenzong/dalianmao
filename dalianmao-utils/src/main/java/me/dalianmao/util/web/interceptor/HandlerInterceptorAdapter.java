package me.dalianmao.util.web.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import me.dalianmao.util.web.WebUtil;

/**
 *
 * @author xiezhenzong
 *
 */
public abstract class HandlerInterceptorAdapter implements HandlerInterceptor {

    protected static final Logger LOG = LoggerFactory.getLogger(HandlerInterceptorAdapter.class);

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView)
            throws Exception {
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
            throws Exception {
    }

    protected final boolean isWeixinUserAgent(HttpServletRequest request) {
        return WebUtil.isWeixinUserAgent(request);
    }

}
