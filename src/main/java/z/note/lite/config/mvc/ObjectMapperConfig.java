package z.note.lite.config.mvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ObjectMapperConfig {

    @Bean
    @ConditionalOnBean(ObjectMapper.class)
    public ObjectMapper objectMapper(ObjectMapper objectMapper) {
        // TODO customize objectMapper
        return objectMapper;
    }

}
