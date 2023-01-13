package z.note.lite.config.preferences;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.bind.DefaultValue;

@ConfigurationProperties(prefix = "preferences.admin")
public record AdminProperties(
        @DefaultValue("admin") String supervisor) {
}
