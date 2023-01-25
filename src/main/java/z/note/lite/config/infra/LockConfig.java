package z.note.lite.config.infra;

import z.note.lite.infra.Lock;
import z.note.lite.infra.impl.JdkLock;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration(proxyBeanMethods = false)
public class LockConfig {

    @ConditionalOnProperty(prefix = "infra.lock", name = "type", havingValue = "jdk")
    @Bean
    public Lock jdkLock() {
        return new JdkLock();
    }
}
