package dev.z.blog.config.logging;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.MDC;
import org.springframework.web.servlet.HandlerInterceptor;

public class TraceIdInterceptor implements HandlerInterceptor {

    private static final String TRACE_ID = "traceId";

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        MDC.put(TRACE_ID, String.format("TraceId=%-15s", System.currentTimeMillis()));
        return HandlerInterceptor.super.preHandle(request, response, handler);
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        MDC.remove(TRACE_ID);
        HandlerInterceptor.super.afterCompletion(request, response, handler, ex);
    }

}
