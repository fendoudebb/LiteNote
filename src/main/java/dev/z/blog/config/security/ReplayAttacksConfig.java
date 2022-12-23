package dev.z.blog.config.security;

import dev.z.blog.constant.mvc.Url;
import dev.z.blog.filter.security.ReplayAttacksFilter;
import dev.z.blog.infra.Cache;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Collections;

@Configuration(proxyBeanMethods = false)
public class ReplayAttacksConfig {

    @Bean
    public FilterRegistrationBean<ReplayAttacksFilter> filterRegistrationBean(Cache cache) {
        FilterRegistrationBean<ReplayAttacksFilter> filterRegistrationBean = new FilterRegistrationBean<>();
        filterRegistrationBean.setFilter(new ReplayAttacksFilter(cache));
        filterRegistrationBean.setOrder(FilterRegistrationBean.HIGHEST_PRECEDENCE);
        filterRegistrationBean.setUrlPatterns(Collections.singleton(Url.Api.FILTER_PATTERN));
        return filterRegistrationBean;
    }

}
