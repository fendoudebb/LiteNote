package dev.z.blog.config.blog;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "blog.portal")
public record PortalProperties(
        String title,
        String author,
        String keywords,
        String github) {
}
