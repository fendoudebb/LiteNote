package z.note.lite.config.interceptor;

import jakarta.annotation.Resource;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import z.note.lite.constant.mvc.Endpoint;
import z.note.lite.infra.RateLimiter;
import z.note.lite.interceptor.RateLimiterInterceptor;

@Configuration(proxyBeanMethods = false)
public class InterceptorConfig implements WebMvcConfigurer {

    @Resource
    private RateLimiter rateLimiter;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new RateLimiterInterceptor(rateLimiter))
                .addPathPatterns(Endpoint.Api.PATTERN); // /**
    }
}

