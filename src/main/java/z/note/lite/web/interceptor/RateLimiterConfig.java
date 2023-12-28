package z.note.lite.web.interceptor;

import jakarta.annotation.Resource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.TaskScheduler;
import z.note.lite.infra.RateLimiter;
import z.note.lite.infra.impl.JdkRateLimiter;

@Configuration(proxyBeanMethods = false)
public class RateLimiterConfig {

    @Resource
    private RateLimiterProperties rateLimiterProperties;

    @Bean
    public RateLimiter rateLimiter(TaskScheduler taskScheduler) {
//        if (Objects.equals(rateLimiterProperties.type(), "jdk")) {}
        return new JdkRateLimiter(taskScheduler, rateLimiterProperties.getDuration(), rateLimiterProperties.getCount());
    }

}
