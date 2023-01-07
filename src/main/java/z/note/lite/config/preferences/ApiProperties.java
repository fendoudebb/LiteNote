package z.note.lite.config.preferences;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.bind.DefaultValue;

@ConfigurationProperties(prefix = "preferences.api")
public record ApiProperties(
        @DefaultValue("session") String login,
        @DefaultValue("graphic") String captcha) {
}
