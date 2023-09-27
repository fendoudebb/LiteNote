package z.note.lite.web.i18n;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.context.MessageSourceProperties;
import org.springframework.boot.autoconfigure.web.WebProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.i18n.AcceptHeaderLocaleResolver;
import org.springframework.web.util.WebUtils;

import java.time.Duration;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

@Configuration(proxyBeanMethods = false)
public class LocaleConfig {

    @Bean
    public LocaleResolver localeResolver(WebProperties webProperties) {
        AcceptHeaderLocaleResolver localeResolver = new AcceptHeaderLocaleResolver() {
            @Override
            public Locale resolveLocale(HttpServletRequest request) {
                String lang = request.getParameter("lang");
                String sessionLang = (String) WebUtils.getSessionAttribute(request, "lang");
                if (StringUtils.hasText(lang)) {
                    if (!Objects.equals(lang, sessionLang)) {
                        WebUtils.setSessionAttribute(request, "lang", lang);
                    }
                    return StringUtils.parseLocaleString(lang);
                }
                if (StringUtils.hasText(sessionLang)) {
                    return StringUtils.parseLocaleString(sessionLang);
                }
                return super.resolveLocale(request);
            }
        };
        localeResolver.setDefaultLocale(webProperties.getLocale());
        localeResolver.setSupportedLocales(List.of(Locale.CHINESE, Locale.ENGLISH));
        return localeResolver;
    }

//    @Bean
//    @ConditionalOnBean(LocaleResolver.class)
//    public LocaleResolver localeResolver(LocaleResolver localeResolver) {
//        if (localeResolver instanceof AcceptHeaderLocaleResolver acceptHeaderLocaleResolver) {
//            acceptHeaderLocaleResolver.setDefaultLocale(Locale.ENGLISH);
//            acceptHeaderLocaleResolver.setSupportedLocales(List.of(Locale.CHINESE, Locale.ENGLISH));
//        }
//        return localeResolver;
//    }

    @Bean
    @ConditionalOnMissingBean(MessageSourceProperties.class)
    @ConfigurationProperties(prefix = "spring.messages")
    public MessageSourceProperties messageSourceProperties() {
        return new MessageSourceProperties();
    }

    @Bean(name = AbstractApplicationContext.MESSAGE_SOURCE_BEAN_NAME)
    public MessageSource messageSource(MessageSourceProperties properties) {
        ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
        if (StringUtils.hasText(properties.getBasename())) {
            messageSource.setBasenames(StringUtils
                    .commaDelimitedListToStringArray(StringUtils.trimAllWhitespace(properties.getBasename())));
        }
        if (properties.getEncoding() != null) {
            messageSource.setDefaultEncoding(properties.getEncoding().name());
        }
        messageSource.setFallbackToSystemLocale(properties.isFallbackToSystemLocale());
        Duration cacheDuration = properties.getCacheDuration();
        if (cacheDuration != null) {
            messageSource.setCacheMillis(cacheDuration.toMillis());
        }
        messageSource.setAlwaysUseMessageFormat(properties.isAlwaysUseMessageFormat());
        messageSource.setUseCodeAsDefaultMessage(properties.isUseCodeAsDefaultMessage());
        messageSource.clearCacheIncludingAncestors();
        return messageSource;
    }

}
