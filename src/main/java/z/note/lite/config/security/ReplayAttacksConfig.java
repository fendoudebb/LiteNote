package z.note.lite.config.security;

import z.note.lite.constant.mvc.Endpoint;
import z.note.lite.filter.security.ReplayAttacksFilter;
import z.note.lite.infra.Cache;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Collections;

@Configuration(proxyBeanMethods = false)
public class ReplayAttacksConfig {

    /**
     * after {@link z.note.lite.filter.trace.TraceFilter}
     */
    @Bean
    public FilterRegistrationBean<ReplayAttacksFilter> replayAttacksFilter(Cache cache) {
        FilterRegistrationBean<ReplayAttacksFilter> filterRegistrationBean = new FilterRegistrationBean<>();
        filterRegistrationBean.setFilter(new ReplayAttacksFilter(cache));
        filterRegistrationBean.setOrder(FilterRegistrationBean.HIGHEST_PRECEDENCE + 1); // after TraceFilter
        filterRegistrationBean.setUrlPatterns(Collections.singleton(Endpoint.Api.FILTER_PATTERN));
        return filterRegistrationBean;
    }

}
