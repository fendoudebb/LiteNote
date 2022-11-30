package dev.z.blog.config.logging;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.MDC;
import org.springframework.web.servlet.HandlerInterceptor;

public class SpanIdInterceptor implements HandlerInterceptor {

    private static final String SPAN_ID = "spanId";

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        MDC.put(SPAN_ID, String.format("spanId#%-15s", System.currentTimeMillis()));
        return HandlerInterceptor.super.preHandle(request, response, handler);
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        MDC.remove(SPAN_ID);
        HandlerInterceptor.super.afterCompletion(request, response, handler, ex);
    }

}
