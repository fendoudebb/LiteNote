package dev.z.blog.config.mvc;

import dev.z.blog.config.logging.TraceIdInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    private final TraceIdInterceptor traceIdInterceptor;

    public WebMvcConfig() {
        this.traceIdInterceptor = new TraceIdInterceptor();
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(traceIdInterceptor).addPathPatterns("/**");
    }

}
