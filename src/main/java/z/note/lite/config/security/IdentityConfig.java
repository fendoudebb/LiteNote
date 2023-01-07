package z.note.lite.config.security;

import z.note.lite.infra.Cache;
import z.note.lite.security.filter.IdentityFilter;
import z.note.lite.security.filter.impl.JwtIdentityFilter;
import z.note.lite.security.filter.impl.SessionIdentityFilter;
import z.note.lite.service.admin.CaptchaService;
import z.note.lite.service.admin.LoginService;
import z.note.lite.service.admin.impl.GraphicCaptchaService;
import z.note.lite.service.admin.impl.JwtLoginService;
import z.note.lite.service.admin.impl.SessionLoginService;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;

@Configuration
public class IdentityConfig {

    @Bean
    @ConditionalOnProperty(prefix = "preferences.api", name = "login", havingValue = "session")
    public IdentityFilter sessionIdentityFilter() {
        return new SessionIdentityFilter();
    }

    @Bean
    @ConditionalOnProperty(prefix = "preferences.api", name = "login", havingValue = "jwt")
    public IdentityFilter jwtIdentityFilter() {
        return new JwtIdentityFilter();
    }

    @Bean
    @ConditionalOnProperty(prefix = "preferences.api", name = "login", havingValue = "session")
    public LoginService sessionLoginService(AuthenticationManager authenticationManager) {
        return new SessionLoginService(authenticationManager);
    }

    @Bean
    @ConditionalOnProperty(prefix = "preferences.api", name = "login", havingValue = "jwt")
    public LoginService jwtLoginService(AuthenticationManager authenticationManager) {
        return new JwtLoginService(authenticationManager);
    }

    @Bean
    @ConditionalOnProperty(prefix = "preferences.api", name = "captcha", havingValue = "graphic")
    public CaptchaService graphicCaptchaService(Cache cache) {
        return new GraphicCaptchaService(cache);
    }

}
