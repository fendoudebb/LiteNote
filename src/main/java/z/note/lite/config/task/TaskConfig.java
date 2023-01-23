package z.note.lite.config.task;

import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;

@Configuration(proxyBeanMethods = false)
public class TaskConfig {

    @Bean
    @ConditionalOnBean
    public ThreadPoolTaskExecutor threadPoolTaskExecutor(ThreadPoolTaskExecutor threadPoolTaskExecutor) {
        return threadPoolTaskExecutor;
    }

    @Bean
    @ConditionalOnBean
    public ThreadPoolTaskScheduler threadPoolTaskScheduler(ThreadPoolTaskScheduler threadPoolTaskScheduler) {
        return threadPoolTaskScheduler;
    }

}
