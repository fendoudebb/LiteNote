package z.note.lite.config.security;

import jakarta.annotation.Resource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.ExceptionTranslationFilter;
import z.note.lite.config.security.filter.IdentityFilter;
import z.note.lite.config.security.handler.ApiAuthenticationEntryPoint;
import z.note.lite.controller.Endpoint;
import z.note.lite.config.security.handler.ApiAccessDeniedHandler;

@EnableWebSecurity
@Configuration(proxyBeanMethods = false)
public class WebSecurityConfig {

    @Resource
    private IdentityFilter identityFilter;

    @Bean
    @Order(1)
    public SecurityFilterChain apiFilterChain(HttpSecurity http) throws Exception {
        return http
                .securityMatcher(Endpoint.Api.PATTERN, Endpoint.Admin.PATTERN)
                .csrf(AbstractHttpConfigurer::disable)
                .headers(AbstractHttpConfigurer::disable)
                .requestCache(AbstractHttpConfigurer::disable)
                .anonymous(AbstractHttpConfigurer::disable)
                .httpBasic(AbstractHttpConfigurer::disable)
                .formLogin(AbstractHttpConfigurer::disable)
                .logout(AbstractHttpConfigurer::disable)
                .addFilterBefore(identityFilter, ExceptionTranslationFilter.class)
                .authorizeHttpRequests(authorizeHttpRequests -> {
                    authorizeHttpRequests.requestMatchers(HttpMethod.GET, Endpoint.Admin.LOGIN).permitAll();
                    authorizeHttpRequests.requestMatchers(HttpMethod.POST, Endpoint.Api.LOGIN).permitAll();
                    authorizeHttpRequests.requestMatchers(HttpMethod.GET, Endpoint.Api.CAPTCHA).permitAll();
//                    authorizeHttpRequests.anyRequest().access(new AdminAuthorizationManager(admin));
                    authorizeHttpRequests.anyRequest().authenticated();
                })
                .exceptionHandling(exceptionHandling -> {
                    exceptionHandling.authenticationEntryPoint(new ApiAuthenticationEntryPoint());
                    exceptionHandling.accessDeniedHandler(new ApiAccessDeniedHandler());
                })
                .build();
    }

//    @Bean
//    public AuthenticationProvider authenticationProvider(UserDetailsService userDetailsService, Cache cache) {
//        IdentityAuthenticationProvider authenticationProvider = new IdentityAuthenticationProvider(cache);
//        authenticationProvider.setUserDetailsService(userDetailsService);
//        return authenticationProvider;
//    }
//
//    @Bean
//    public UserDetailsService userDetailsService(SysUserRepository sysUserRepository) {
//        return new SysUserDetailService(sysUserRepository);
//    }
//
//    @Bean
//    @ConditionalOnMissingBean(AuthenticationManager.class)
//    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
//        return configuration.getAuthenticationManager();
//    }
//
//    @Bean
//    public PasswordEncoder passwordEncoder() {
//        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
//    }

//    @Bean
//    public SecurityFilterChain portalFilterChain(HttpSecurity http) throws Exception {
//        return http
//                .securityMatcher(Endpoint.Portal.PATTERN)
//                .sessionManagement(AbstractHttpConfigurer::disable)
//                .securityContext(AbstractHttpConfigurer::disable)
//                .headers(AbstractHttpConfigurer::disable)
//                .csrf(AbstractHttpConfigurer::disable)
//                .logout(AbstractHttpConfigurer::disable)
//                .requestCache(AbstractHttpConfigurer::disable)
//                .anonymous(AbstractHttpConfigurer::disable)
//                .authorizeHttpRequests(authorizeHttpRequests -> authorizeHttpRequests.anyRequest().permitAll())
//                .build();
//    }

}
