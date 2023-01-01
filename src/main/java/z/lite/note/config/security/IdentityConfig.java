package z.lite.note.config.security;

import z.lite.note.infra.Cache;
import z.lite.note.security.filter.IdentityFilter;
import z.lite.note.security.filter.impl.JwtIdentityFilter;
import z.lite.note.security.filter.impl.SessionIdentityFilter;
import z.lite.note.service.admin.CaptchaService;
import z.lite.note.service.admin.LoginService;
import z.lite.note.service.admin.impl.GraphicCaptchaService;
import z.lite.note.service.admin.impl.JwtLoginService;
import z.lite.note.service.admin.impl.SessionLoginService;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;

@Configuration
public class IdentityConfig {

    @Bean
    @ConditionalOnProperty(prefix = "blog.api", name = "login", havingValue = "session")
    public IdentityFilter sessionIdentityFilter() {
        return new SessionIdentityFilter();
    }

    @Bean
    @ConditionalOnProperty(prefix = "blog.api", name = "login", havingValue = "jwt")
    public IdentityFilter jwtIdentityFilter() {
        return new JwtIdentityFilter();
    }

    @Bean
    @ConditionalOnProperty(prefix = "blog.api", name = "login", havingValue = "session")
    public LoginService sessionLoginService(AuthenticationManager authenticationManager) {
        return new SessionLoginService(authenticationManager);
    }

    @Bean
    @ConditionalOnProperty(prefix = "blog.api", name = "login", havingValue = "jwt")
    public LoginService jwtLoginService(AuthenticationManager authenticationManager) {
        return new JwtLoginService(authenticationManager);
    }

    @Bean
    @ConditionalOnProperty(prefix = "blog.api", name = "captcha", havingValue = "graphic")
    public CaptchaService graphicCaptchaService(Cache cache) {
        return new GraphicCaptchaService(cache);
    }

}
