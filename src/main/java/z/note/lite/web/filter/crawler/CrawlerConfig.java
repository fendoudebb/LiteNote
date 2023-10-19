package z.note.lite.web.filter.crawler;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import z.note.lite.web.Endpoint;
import z.note.lite.web.filter.trace.TraceFilter;

import java.util.Collections;

@Configuration(proxyBeanMethods = false)
public class CrawlerConfig {

    @Bean
    public FilterRegistrationBean<CrawlerFilter> crawlerFilter() {
        FilterRegistrationBean<CrawlerFilter> filterRegistrationBean = new FilterRegistrationBean<>();
        filterRegistrationBean.setFilter(new CrawlerFilter());
        filterRegistrationBean.setUrlPatterns(Collections.singleton(Endpoint.Api.FILTER_PATTERN));
        return filterRegistrationBean;
    }

}
