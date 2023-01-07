package z.note.lite.config.preferences;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.bind.DefaultValue;

@ConfigurationProperties(prefix = "preferences.portal")
public record PortalProperties(
        @DefaultValue("Blog") String title,
        @DefaultValue("fendoudebb") String author,
        @DefaultValue("Spring Native, Spring Boot") String keywords,
        @DefaultValue("https://github.com/fendoudebb/z-blog-spring-native") String github) {
}
