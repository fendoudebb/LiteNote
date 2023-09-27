package z.note.lite.web.interceptor;

import jakarta.annotation.Resource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import z.note.lite.infra.RateLimiter;
import z.note.lite.infra.impl.JdkRateLimiter;

@Configuration(proxyBeanMethods = false)
public class RateLimiterConfig {

    @Resource
    private RateLimiterProperties rateLimiterProperties;

    @Bean
    public RateLimiter rateLimiter(ThreadPoolTaskScheduler scheduler) {
//        if (Objects.equals(rateLimiterProperties.type(), "jdk")) {}
        return new JdkRateLimiter(scheduler, rateLimiterProperties.getDuration(), rateLimiterProperties.getCount());
    }

}
