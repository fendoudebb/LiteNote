package z.lite.note.config.property;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.bind.DefaultValue;

@ConfigurationProperties(prefix = "blog.api")
public record ApiProperties(
        @DefaultValue("session") String login,
        @DefaultValue("graphic") String captcha) {
}
