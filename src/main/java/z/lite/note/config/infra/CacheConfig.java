package z.lite.note.config.infra;

import z.lite.note.infra.Cache;
import z.lite.note.infra.impl.JdkCache;
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
