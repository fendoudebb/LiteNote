package z.lite.note.config.security;

import z.lite.note.constant.mvc.Url;
import z.lite.note.filter.security.ReplayAttacksFilter;
import z.lite.note.infra.Cache;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Collections;

@Configuration(proxyBeanMethods = false)
public class ReplayAttacksConfig {

    @Bean
    public FilterRegistrationBean<ReplayAttacksFilter> replayAttacksFilter(Cache cache) {
        FilterRegistrationBean<ReplayAttacksFilter> filterRegistrationBean = new FilterRegistrationBean<>();
        filterRegistrationBean.setFilter(new ReplayAttacksFilter(cache));
        filterRegistrationBean.setOrder(FilterRegistrationBean.HIGHEST_PRECEDENCE + 1); // after TraceFilter
        filterRegistrationBean.setUrlPatterns(Collections.singleton(Url.Api.FILTER_PATTERN));
        return filterRegistrationBean;
    }

}
