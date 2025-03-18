package org.dop.config;

import com.nimbusds.jose.shaded.gson.Gson;
import com.nimbusds.jose.shaded.gson.GsonBuilder;
import org.dop.module.helper.InstantTypeAdapter;
import org.dop.module.security.tenant.filter.TenantContextRequestFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.Instant;

@EnableJpaAuditing
@Configuration
public class DogConfig {

    @Bean
    public PasswordEncoder passwordEncoder(){
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    @Bean
    public Gson gson(){
        return new GsonBuilder()
                .registerTypeAdapter(Instant.class, new InstantTypeAdapter())
                .create();
    }

    @Bean
    public FilterRegistrationBean<TenantContextRequestFilter> routingDataSourceRequestFilter() {
        FilterRegistrationBean<TenantContextRequestFilter> registrationBean = new FilterRegistrationBean<>();
        registrationBean.setFilter(new TenantContextRequestFilter());
        registrationBean.addUrlPatterns("/*");
        registrationBean.setOrder(Ordered.HIGHEST_PRECEDENCE);
        return registrationBean;
    }

}
