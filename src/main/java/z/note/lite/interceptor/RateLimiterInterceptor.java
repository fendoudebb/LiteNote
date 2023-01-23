package z.note.lite.interceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.HandlerMapping;
import z.note.lite.infra.RateLimiter;

@Slf4j
@RequiredArgsConstructor
public class RateLimiterInterceptor implements HandlerInterceptor {

    private final RateLimiter rateLimiter;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        log.info("handler name: {}", handler.getClass().getName());
        Object attribute = request.getAttribute(HandlerMapping.BEST_MATCHING_HANDLER_ATTRIBUTE);
        if (handler instanceof HandlerMethod hm) {
            if (hm.hasMethodAnnotation(z.note.lite.infra.annoation.RateLimiter.class)) {
//                z.note.lite.infra.annoation.RateLimiter limiter = hm.getMethodAnnotation(z.note.lite.infra.annoation.RateLimiter.class);
                String username = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
                rateLimiter.latch(String.format("%s %s %s", username, request.getMethod(), request.getRequestURI()));
            }
        }
        return HandlerInterceptor.super.preHandle(request, response, handler);
    }

}
