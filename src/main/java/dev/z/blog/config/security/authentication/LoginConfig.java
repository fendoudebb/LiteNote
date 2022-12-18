package dev.z.blog.config.security.authentication;

import dev.z.blog.service.admin.LoginService;
import dev.z.blog.service.admin.impl.SessionLoginService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;

@Configuration
public class LoginConfig {

    @Bean
    public LoginService loginService(AuthenticationManager authenticationManager) {
        return new SessionLoginService(authenticationManager);
    }

}
