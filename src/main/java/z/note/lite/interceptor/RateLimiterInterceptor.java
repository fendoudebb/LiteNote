package z.note.lite.interceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import z.note.lite.infra.RateLimiter;
import z.note.lite.util.RequestUtils;

import java.util.Objects;

@Slf4j
@RequiredArgsConstructor
public class RateLimiterInterceptor implements HandlerInterceptor {

    private final RateLimiter rateLimiter;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (handler instanceof HandlerMethod hm) {
            if (hm.hasMethodAnnotation(z.note.lite.infra.annotation.RateLimiter.class)) {
//                z.note.lite.infra.annotation.RateLimiter limiter = hm.getMethodAnnotation(z.note.lite.infra.annotation.RateLimiter.class);
                Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
                String key;
                if (Objects.isNull(authentication)) {
                    key = RequestUtils.getIp();
                } else {
                    key = (String) authentication.getPrincipal();
                }
                rateLimiter.latch(String.format("%s %s %s", key, request.getMethod(), request.getRequestURI()));
            }
        }
        return HandlerInterceptor.super.preHandle(request, response, handler);
    }

}
