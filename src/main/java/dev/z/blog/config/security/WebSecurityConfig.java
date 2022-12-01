package dev.z.blog.config.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class WebSecurityConfig {

    @Profile("dev")
    @EnableWebSecurity(debug = true)
    @SuppressWarnings("unused")
    static class Dev {}

    @Profile("prod")
    @EnableWebSecurity
    @SuppressWarnings("unused")
    static class Prod {}


    @Bean
    @Order(1)
    public SecurityFilterChain apiFilterChain(HttpSecurity http) throws Exception {
        http
             .securityMatcher("/api/**")
                .csrf().disable()
                .httpBasic().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
             .and()
                .authorizeHttpRequests()
                .requestMatchers( "/api/login")
                .permitAll()
            .and()
                .authorizeHttpRequests()
                .anyRequest()
                .authenticated()
            .and()
                .exceptionHandling()
                .authenticationEntryPoint((request, response, authException) -> {
                    System.out.println("auth exception#" + request.toString());
                })
                .accessDeniedHandler((request, response, accessDeniedException) -> {
                    System.out.println("access deny#" + request.toString());
                });
        return http.build();
    }

    @Bean
    SecurityFilterChain portalFilterChain(HttpSecurity http) throws Exception {
        http
             .securityMatcher("/**")
                .authorizeHttpRequests()
                .anyRequest()
                .permitAll()
             .and()
                .formLogin()
                .disable()
                .logout()
                .disable()
//                .headers().frameOptions().disable(); // resolve iframe x-frame-options deny
                .headers().frameOptions().sameOrigin(); // iframe url must be same origin
        return http.build();

    }

}
