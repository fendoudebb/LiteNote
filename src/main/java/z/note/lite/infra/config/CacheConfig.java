package z.note.lite.infra.config;

import z.note.lite.infra.Cache;
import z.note.lite.infra.impl.JdkCache;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration(proxyBeanMethods = false)
public class CacheConfig {

    @ConditionalOnProperty(prefix = "infra.cache", name = "type", havingValue = "jdk")
    @Bean
    public Cache jdkCache() {
        return new JdkCache();
    }

}
