package z.note.lite.web.security.config;

import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import z.note.lite.preferences.Admin;
import z.note.lite.web.Endpoint;
import z.note.lite.infra.Cache;
import z.note.lite.repository.api.SysUserRepository;
import z.note.lite.web.security.authentication.provider.IdentityAuthenticationProvider;
import z.note.lite.web.security.authorization.AdminAuthorizationManager;
import z.note.lite.web.security.filter.IdentityFilter;
import z.note.lite.web.security.handler.ApiAccessDeniedHandler;
import z.note.lite.web.security.handler.ApiAuthenticationEntryPoint;
import z.note.lite.service.api.SysUserDetailService;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
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

@EnableWebSecurity
@Configuration(proxyBeanMethods = false)
public class WebSecurityConfig {

    @Bean
    public AuthenticationProvider authenticationProvider(UserDetailsService userDetailsService, Cache cache) {
        IdentityAuthenticationProvider authenticationProvider = new IdentityAuthenticationProvider(cache);
        authenticationProvider.setUserDetailsService(userDetailsService);
        return authenticationProvider;
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
    public SecurityFilterChain apiFilterChain(HttpSecurity http, IdentityFilter identityFilter, Admin admin) throws Exception {
        return http
                .securityMatcher(Endpoint.Api.PATTERN, Endpoint.Admin.PATTERN)
                .csrf(AbstractHttpConfigurer::disable)
                .headers(AbstractHttpConfigurer::disable)
                .requestCache(AbstractHttpConfigurer::disable)
                .anonymous(AbstractHttpConfigurer::disable)
                .httpBasic(AbstractHttpConfigurer::disable)
                .formLogin(AbstractHttpConfigurer::disable)
                .logout(AbstractHttpConfigurer::disable)
                .addFilterAfter(identityFilter, SecurityContextHolderAwareRequestFilter.class)
                .authorizeHttpRequests(authorizeHttpRequests -> {
                    authorizeHttpRequests.requestMatchers(HttpMethod.GET, Endpoint.Admin.LOGIN).permitAll();
                    authorizeHttpRequests.requestMatchers(HttpMethod.POST, Endpoint.Api.LOGIN).permitAll();
                    authorizeHttpRequests.requestMatchers(HttpMethod.GET, Endpoint.Api.CAPTCHA).permitAll();
                    authorizeHttpRequests.anyRequest().access(new AdminAuthorizationManager(admin));
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
                .securityMatcher(Endpoint.Portal.PATTERN)
                .csrf(AbstractHttpConfigurer::disable)
                .requestCache(AbstractHttpConfigurer::disable)
//                .anonymous().disable()
                .httpBasic(AbstractHttpConfigurer::disable)
                .formLogin(AbstractHttpConfigurer::disable)
                .logout(AbstractHttpConfigurer::disable)
                .headers(headersSecurity -> {
                    headersSecurity.frameOptions(HeadersConfigurer.FrameOptionsConfig::disable); // resolve iframe x-frame-options deny
                    headersSecurity.frameOptions(HeadersConfigurer.FrameOptionsConfig::sameOrigin); // iframe url must be same origin
                })
                .authorizeHttpRequests(authorizeHttpRequests -> authorizeHttpRequests.anyRequest().permitAll())
                .build();
    }

}
