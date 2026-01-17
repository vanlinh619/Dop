package org.dop.config.web;

import org.dop.config.property.LanguageProperties;
import org.dop.entity.state.LanguageCode;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.i18n.CookieLocaleResolver;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;

import java.util.Arrays;
import java.util.Locale;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {


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
    }
}
