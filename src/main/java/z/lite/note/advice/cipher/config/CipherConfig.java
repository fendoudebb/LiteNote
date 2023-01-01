package z.lite.note.advice.cipher.config;

import z.lite.note.advice.cipher.CipherService;
import z.lite.note.advice.cipher.impl.Base64CipherService;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration(proxyBeanMethods = false)
@ConditionalOnMissingBean(CipherService.class)
public class CipherConfig {

    @Bean
    public CipherService cipherService() {
        return new Base64CipherService();
    }

}

