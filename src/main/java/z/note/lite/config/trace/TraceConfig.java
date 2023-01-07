package z.note.lite.config.trace;

import z.note.lite.constant.mvc.Url;
import z.note.lite.filter.trace.TraceFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Collections;

@Configuration(proxyBeanMethods = false)
public class TraceConfig {

    @Bean
    public FilterRegistrationBean<TraceFilter> traceFilter() {
        FilterRegistrationBean<TraceFilter> filterRegistrationBean = new FilterRegistrationBean<>();
        filterRegistrationBean.setFilter(new TraceFilter());
        filterRegistrationBean.setOrder(FilterRegistrationBean.HIGHEST_PRECEDENCE);
        filterRegistrationBean.setUrlPatterns(Collections.singleton(Url.Root.FILTER_PATTERN));
        return filterRegistrationBean;
    }

}
