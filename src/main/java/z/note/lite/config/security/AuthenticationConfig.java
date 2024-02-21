package z.note.lite.config.security;

import z.note.lite.config.security.filter.IdentityFilter;
import z.note.lite.infra.Cache;
import z.note.lite.mapper.api.SysUserMapper;
import z.note.lite.config.security.filter.impl.JwtIdentityFilter;
import z.note.lite.config.security.filter.impl.SessionIdentityFilter;
import z.note.lite.service.api.CaptchaService;
import z.note.lite.service.api.LoginService;
import z.note.lite.service.api.impl.GraphicCaptchaService;
import z.note.lite.service.api.impl.JwtLoginService;
import z.note.lite.service.api.impl.SessionLoginService;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AuthenticationConfig {

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
    public LoginService sessionLoginService(SysUserMapper sysUserMapper, Cache cache) {
        return new SessionLoginService(sysUserMapper, cache);
    }

    @Bean
    @ConditionalOnProperty(prefix = "preferences.api", name = "login", havingValue = "jwt")
    public LoginService jwtLoginService() {
        return new JwtLoginService();
    }

    @Bean
    @ConditionalOnProperty(prefix = "preferences.api", name = "captcha", havingValue = "graphic")
    public CaptchaService graphicCaptchaService(Cache cache) {
        return new GraphicCaptchaService(cache);
    }

}
