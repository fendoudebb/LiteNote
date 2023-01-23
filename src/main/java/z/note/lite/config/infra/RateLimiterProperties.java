package z.note.lite.config.infra;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.bind.DefaultValue;

import java.time.Duration;

@ConfigurationProperties(prefix = "infra.rate-limiter")
public record RateLimiterProperties(
        @DefaultValue("jdk") String type,
        @DefaultValue("1m") Duration duration,
        @DefaultValue("0") int count) {
}