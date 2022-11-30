package dev.z.blog.config.mvc;

import dev.z.blog.config.logging.SpanIdInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    private final SpanIdInterceptor spanIdInterceptor;

    public WebMvcConfig() {
        this.spanIdInterceptor = new SpanIdInterceptor();
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(spanIdInterceptor).addPathPatterns("/**");
    }

}
