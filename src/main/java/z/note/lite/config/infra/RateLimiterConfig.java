package z.note.lite.config.infra;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import z.note.lite.infra.RateLimiter;
import z.note.lite.infra.impl.JdkRateLimiter;

@RequiredArgsConstructor
@Configuration(proxyBeanMethods = false)
public class RateLimiterConfig {

    private final RateLimiterProperties rateLimiterProperties;

    @Bean
    public RateLimiter rateLimiter(ThreadPoolTaskScheduler scheduler) {
//        if (Objects.equals(rateLimiterProperties.type(), "jdk")) {}
        return new JdkRateLimiter(scheduler, rateLimiterProperties.duration(), rateLimiterProperties.count());
    }

}
