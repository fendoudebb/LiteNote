package z.note.lite.config.infra;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.time.Duration;

@Setter
@Getter
@ToString
@ConfigurationProperties(prefix = "infra.rate-limiter")
public class RateLimiterProperties {

    private String type;

    private Duration duration;

    private int count;

}
