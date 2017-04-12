package me.dalianmao.util.web.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import me.dalianmao.util.log.AccessLogger;

/**
 * 记录接口的访问，子类通过覆盖{@code createAccessLog}来覆盖访问日志的内容
 *
 * @author xiezhenzong
 *
 */
public class AccessLogInterceptor extends HandlerInterceptorAdapter {

    public static final String START_TIME_KEY = "start_time";

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        request.setAttribute(START_TIME_KEY, System.currentTimeMillis());
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        long startTime = (Long) request.getAttribute(START_TIME_KEY);
        long endTime = System.currentTimeMillis();
        AccessLogger.info(createAccessLog(request, response, startTime, endTime));
    }

    protected String createAccessLog(HttpServletRequest request, HttpServletResponse response, long startTime, long endTime) {
        // @formatter:off
        StringBuilder builder = new StringBuilder("[path=").append(request.getRequestURI())
                .append("][starttime=").append(startTime)
                .append("][timecost=").append(endTime - startTime).append("]");
        // @formatter:on
        return builder.toString();
    }
}
