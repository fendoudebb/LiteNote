package dev.z.blog.config.infra;

import dev.z.blog.infra.Cache;
import dev.z.blog.infra.impl.JdkCache;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CacheConfig {

    @ConditionalOnProperty(prefix = "infra.cache", name = "type", havingValue = "jdk")
    @Bean
    public Cache jdkCache() {
        return new JdkCache();
    }

}
