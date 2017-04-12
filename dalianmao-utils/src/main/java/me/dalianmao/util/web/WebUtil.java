package me.dalianmao.util.web;

import javax.servlet.http.HttpServletRequest;

/**
 * web工具方法
 *
 * @author xiezhenzong
 *
 */
public abstract class WebUtil {

    public static boolean isWeixinUserAgent(HttpServletRequest request) {
        String userAgent = request.getHeader("User-Agent");
        return userAgent != null && userAgent.contains("MicroMessenger");
    }
}
