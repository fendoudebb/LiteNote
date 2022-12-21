package dev.z.blog.config.property;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.bind.DefaultValue;

@ConfigurationProperties(prefix = "blog.portal")
public record PortalProperties(
        @DefaultValue("Blog") String title,
        @DefaultValue("fendoudebb") String author,
        @DefaultValue("Spring Native, Spring Boot") String keywords,
        @DefaultValue("https://github.com/fendoudebb/z-blog-spring-native") String github) {
}
