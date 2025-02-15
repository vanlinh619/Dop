package org.dop.config;

import org.dop.config.property.Oauth2LoginProperties;
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
            Oauth2LoginProperties.SocialProperties googleProperties = oauth2LoginProperties.getSocials()
                    .get(Oauth2LoginProperties.SocialProvider.GOOGLE.getProvider());

            if (googleProperties.isEnable()) {
                return CommonOAuth2Provider.GOOGLE.getBuilder(Oauth2LoginProperties.SocialProvider.GOOGLE.getProvider())
                        .clientId(googleProperties.getClientId())
                        .clientSecret(googleProperties.getClientSecret())
                        .build();
            } else {
                return null;
            }
        };
    }


}
