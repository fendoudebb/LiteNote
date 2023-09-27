package z.note.lite.web.interceptor;

import jakarta.annotation.Resource;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import z.note.lite.web.Endpoint;
import z.note.lite.infra.RateLimiter;

@Configuration(proxyBeanMethods = false)
public class InterceptorConfig implements WebMvcConfigurer {

    @Resource
    private RateLimiter rateLimiter;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new RateLimiterInterceptor(rateLimiter))
                .addPathPatterns(Endpoint.Api.PATTERN); // /api/**
    }
}

