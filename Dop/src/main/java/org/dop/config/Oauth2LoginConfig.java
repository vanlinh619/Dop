package org.dop.config;

import org.dop.config.property.Oauth2LoginProperties;
import org.dop.entity.state.Provider;
import org.dop.module.security.oauth2login.service.DopOidcUserService;
import org.dop.module.user.service.UserInfoService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.oauth2.client.CommonOAuth2Provider;
import org.springframework.security.oauth2.client.registration.ClientRegistration;

import java.util.function.Supplier;

@Configuration
public class Oauth2LoginConfig {

    @Bean
    public Supplier<ClientRegistration> googleClientRegistrations(Oauth2LoginProperties oauth2LoginProperties) {
        return () -> {
            if (oauth2LoginProperties.isSocialEnable(Provider.GOOGLE)) {
                Oauth2LoginProperties.SocialProperties googleProperties = oauth2LoginProperties.getSocials()
                        .get(Provider.GOOGLE.getProvider());
                return CommonOAuth2Provider.GOOGLE.getBuilder(Provider.GOOGLE.getProvider())
                        .clientId(googleProperties.getClientId())
                        .clientSecret(googleProperties.getClientSecret())
                        .build();
            } else {
                return null;
            }
        };
    }

    @Bean
    public Supplier<DopOidcUserService> dopOidcUserService(
            Oauth2LoginProperties oauth2LoginProperties,
            UserInfoService userInfoService
    ) {
        return () -> {
            if (oauth2LoginProperties.isSocialEnable(Provider.GOOGLE)) {
                return new DopOidcUserService(userInfoService);
            } else {
                return null;
            }
        };
    }
}
