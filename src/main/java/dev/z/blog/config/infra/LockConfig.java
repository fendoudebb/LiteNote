package dev.z.blog.config.infra;

import dev.z.blog.infra.Lock;
import dev.z.blog.infra.impl.JdkLock;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class LockConfig {

    @ConditionalOnProperty(prefix = "infra.lock", name = "type", havingValue = "jdk")
    @Bean
    public Lock jdkLock() {
        return new JdkLock();
    }
}
