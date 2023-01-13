package z.note.lite.config.security;

import z.note.lite.config.preferences.AdminProperties;
import z.note.lite.constant.mvc.Url;
import z.note.lite.infra.Cache;
import z.note.lite.repository.api.SysUserRepository;
import z.note.lite.security.authentication.provider.IdentityAuthenticationProvider;
import z.note.lite.security.authorization.AdminAuthorizationManager;
import z.note.lite.security.filter.IdentityFilter;
import z.note.lite.security.handler.ApiAccessDeniedHandler;
import z.note.lite.security.handler.ApiAuthenticationEntryPoint;
import z.note.lite.service.api.SysUserDetailService;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.servletapi.SecurityContextHolderAwareRequestFilter;

@Configuration(proxyBeanMethods = false)
public class WebSecurityConfig {

    @Profile("dev")
    @EnableWebSecurity(debug = true)
    @SuppressWarnings("unused")
    static class Dev {

    }

    @Profile("prod")
    @EnableWebSecurity
    static class Prod {

        @Bean
        public AuthenticationProvider authenticationProvider(UserDetailsService userDetailsService, Cache cache) {
            IdentityAuthenticationProvider authenticationProvider = new IdentityAuthenticationProvider(cache);
            authenticationProvider.setUserDetailsService(userDetailsService);
            return authenticationProvider;
        }

    }

    @Bean
    public UserDetailsService userDetailsService(SysUserRepository sysUserRepository) {
        return new SysUserDetailService(sysUserRepository);
    }

    @Bean
    @ConditionalOnMissingBean(AuthenticationManager.class)
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    @Bean
    @Order(1)
    public SecurityFilterChain apiFilterChain(HttpSecurity http, IdentityFilter identityFilter, AdminProperties adminProperties) throws Exception {
        return http
                .securityMatcher(Url.Api.PATTERN)
                .csrf().disable()
                .headers().disable()
                .requestCache().disable()
                .anonymous().disable()
                .httpBasic().disable()
                .formLogin().disable()
                .logout().disable()
                .addFilterAfter(identityFilter, SecurityContextHolderAwareRequestFilter.class)
                .authorizeHttpRequests(authorizeHttpRequests -> {
                    authorizeHttpRequests.requestMatchers(HttpMethod.POST, Url.Api.LOGIN).permitAll();
                    authorizeHttpRequests.requestMatchers(HttpMethod.GET, Url.Api.CAPTCHA).permitAll();
                    authorizeHttpRequests.anyRequest().access(new AdminAuthorizationManager(adminProperties));
                })
                .exceptionHandling(exceptionHandling -> {
                    exceptionHandling.authenticationEntryPoint(new ApiAuthenticationEntryPoint());
                    exceptionHandling.accessDeniedHandler(new ApiAccessDeniedHandler());
                })
                .build();
    }

    @Bean
    public SecurityFilterChain adminFilterChain(HttpSecurity http) throws Exception {
        return http
                .securityMatcher(Url.Admin.PATTERN)
                .csrf().disable()
                .headers().disable()
                .requestCache().disable()
                .anonymous().disable()
                .httpBasic().disable()
                .formLogin().disable()
                .logout().disable()
//                .addFilterAfter(new LoginSessionFilter(), SecurityContextHolderAwareRequestFilter.class)
                .authorizeHttpRequests(authorizeHttpRequests -> {
                    authorizeHttpRequests.requestMatchers(HttpMethod.GET, Url.Admin.LOGIN).permitAll();
                    authorizeHttpRequests.anyRequest().authenticated();
                })
                .exceptionHandling(exceptionHandling -> {
                    exceptionHandling.authenticationEntryPoint(new ApiAuthenticationEntryPoint());
                    exceptionHandling.accessDeniedHandler(new ApiAccessDeniedHandler());
                })
                .build();
    }

    @Bean
    public SecurityFilterChain portalFilterChain(HttpSecurity http) throws Exception {
        return http
                .securityMatcher(Url.Portal.PATTERN)
                .csrf().disable()
                .requestCache().disable()
//                .anonymous().disable()
                .httpBasic().disable()
                .formLogin().disable()
                .logout().disable()
                .headers(headersSecurity -> {
                    headersSecurity.frameOptions().disable(); // resolve iframe x-frame-options deny
                    headersSecurity.frameOptions().sameOrigin(); // iframe url must be same origin
                })
                .authorizeHttpRequests(authorizeHttpRequests -> authorizeHttpRequests.anyRequest().permitAll())
                .build();
    }

}
