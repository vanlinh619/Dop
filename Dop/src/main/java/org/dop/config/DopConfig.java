package org.dop.config;

import com.nimbusds.jose.shaded.gson.Gson;
import com.nimbusds.jose.shaded.gson.GsonBuilder;
import org.dop.module.helper.gsonadapter.InstantTypeAdapter;
import org.dop.module.setting.service.TenantCollectionService;
import org.dop.module.tenant.TenantExtractService;
import org.dop.module.tenant.filter.TenantContextRequestFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import java.time.Instant;

@EnableJpaAuditing
@Configuration
public class DopConfig {

    @Bean
    public Gson gson(){
        return new GsonBuilder()
                .registerTypeAdapter(Instant.class, new InstantTypeAdapter())
                .create();
    }

    @Bean
    public FilterRegistrationBean<TenantContextRequestFilter> routingDataSourceRequestFilter(
            TenantCollectionService tenantCollectionService,
            TenantExtractService tenantExtractService
    ) {
        FilterRegistrationBean<TenantContextRequestFilter> registrationBean = new FilterRegistrationBean<>();
        registrationBean.setFilter(new TenantContextRequestFilter(tenantCollectionService, tenantExtractService));
        registrationBean.addUrlPatterns("/*");
        registrationBean.setOrder(Ordered.HIGHEST_PRECEDENCE);
        return registrationBean;
    }

}
