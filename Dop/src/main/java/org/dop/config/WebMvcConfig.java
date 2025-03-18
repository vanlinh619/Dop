package org.dop.config;

import org.dop.config.property.LanguageProperties;
import org.dop.entity.state.LanguageCode;
import org.dop.module.security.tenant.interceptor.TenantExistInterceptor;
import org.dop.module.setting.service.SchemaCollectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.i18n.CookieLocaleResolver;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    public static final List<String> whiteList = new ArrayList<>(List.of(
            "/css/**", "/js/**", "/error/**", "/favicon.ico"
    ));

    private SchemaCollectionService schemaCollectionService;

    @Autowired
    public void setSchemaCollectionService(SchemaCollectionService schemaCollectionService) {
        this.schemaCollectionService = schemaCollectionService;
    }

    @Bean
    public LocaleResolver localeResolver(LanguageProperties languageProperties) {
        if (Arrays.stream(LanguageCode.values()).noneMatch(languageCode ->
                languageCode.name().equals(languageProperties.getDefaultLanguage()))
        ) {
            throw new IllegalArgumentException(String.format("Language code {%s} is not supported.", languageProperties.getDefaultLanguage()));
        }
        CookieLocaleResolver localeResolver = new CookieLocaleResolver();
        localeResolver.setDefaultLocale(Locale.of(languageProperties.getDefaultLanguage()));
        return localeResolver;
    }

    @Bean
    public MessageSource messageSource() {
        ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
        messageSource.setBasenames(
                "language/consent",
                "language/error",
                "language/login",
                "language/internal"
        );
        messageSource.setDefaultEncoding("UTF-8");
        return messageSource;
    }

    @Bean
    public LocaleChangeInterceptor localeChangeInterceptor() {
        LocaleChangeInterceptor localeChangeInterceptor = new LocaleChangeInterceptor();
        localeChangeInterceptor.setParamName("lang");
        return localeChangeInterceptor;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        /// For language
        registry.addInterceptor(localeChangeInterceptor());
        /// For Multiple tenant
        registry.addInterceptor(new TenantExistInterceptor(schemaCollectionService))
                .excludePathPatterns(whiteList);
    }
}
