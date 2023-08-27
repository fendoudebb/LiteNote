package z.note.lite.infra.impl;

import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import z.note.lite.infra.RateLimiter;
import z.note.lite.infra.exception.RateLimitationException;

import java.time.Duration;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class JdkRateLimiter implements RateLimiter {

    private final Map<String, Integer> limiterMap = new ConcurrentHashMap<>();

    private final Duration period;

    private final Integer limiterCount;

    public JdkRateLimiter(ThreadPoolTaskScheduler scheduler, Duration period, int limiterCount) {
        scheduler.scheduleAtFixedRate(() -> limiterMap.forEach((k, v) -> limiterMap.replace(k, 0)), period);
        this.period = period;
        this.limiterCount = limiterCount;
    }

    @Override
    public void latch(String flag) {
        Integer count = limiterMap.merge(flag, 1, Math::addExact);
        if (count > limiterCount) {
            String format = "Rate Limitation: %s %d seconds %d counts > %d limit counts.";
            throw new RateLimitationException(String.format(format, flag, period.toMinutes(), count, limiterCount));
        }
    }
}
