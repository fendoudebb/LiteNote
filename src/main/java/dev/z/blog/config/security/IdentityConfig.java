package dev.z.blog.config.security;

import dev.z.blog.infra.Cache;
import dev.z.blog.security.filter.IdentityFilter;
import dev.z.blog.security.filter.impl.JwtIdentityFilter;
import dev.z.blog.security.filter.impl.SessionIdentityFilter;
import dev.z.blog.service.admin.CaptchaService;
import dev.z.blog.service.admin.LoginService;
import dev.z.blog.service.admin.impl.GraphicCaptchaService;
import dev.z.blog.service.admin.impl.JwtLoginService;
import dev.z.blog.service.admin.impl.SessionLoginService;
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
