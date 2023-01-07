package z.note.lite.config.security;

import z.note.lite.constant.mvc.Url;
import z.note.lite.filter.security.ReplayAttacksFilter;
import z.note.lite.infra.Cache;
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
