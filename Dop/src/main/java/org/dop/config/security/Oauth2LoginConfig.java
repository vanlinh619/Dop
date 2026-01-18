package org.dop.config.security;

import org.dop.config.property.Oauth2LoginProperties;
import org.dop.config.property.RoleDefaultProperties;
import org.dop.entity.state.Provider;
import org.dop.module.security.oauth2login.service.DopOidcUserService;
import org.dop.module.user.service.Oauth2UserInfoService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.oauth2.client.CommonOAuth2Provider;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserService;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.registration.InMemoryClientRegistrationRepository;
import org.springframework.security.oauth2.client.registration.SupplierClientRegistrationRepository;

import java.util.List;
import java.util.Objects;
import java.util.function.Supplier;

@Configuration
public class Oauth2LoginConfig {

    @Bean
    public ClientRegistrationRepository clientRegistrationRepository(Oauth2LoginProperties oauth2LoginProperties) {

        Supplier<InMemoryClientRegistrationRepository> supplierClientRegistrationRepository = () -> {
            if (oauth2LoginProperties.anySocialEnable()) {
                List<ClientRegistration> clientRegistrations = oauth2LoginProperties.getSocials().entrySet().stream()
                        .map(socialPropertiesEntry -> {
                            if (socialPropertiesEntry.getKey().equals(Provider.GOOGLE.getProvider())) {
                                Oauth2LoginProperties.SocialProperties googleProperties = socialPropertiesEntry.getValue();
                                return googleClientRegistrations(googleProperties);
                            }
                            return null;
                        })
                        .filter(Objects::nonNull)
                        .toList();
                return new InMemoryClientRegistrationRepository(clientRegistrations);
            }
            return null;
        };
        return new SupplierClientRegistrationRepository(supplierClientRegistrationRepository);
    }

    @Bean
    public OidcUserService dopOidcUserService(
            Oauth2UserInfoService oauth2UserInfoService,
            RoleDefaultProperties roleDefaultProperties
    ) {
        return new DopOidcUserService(oauth2UserInfoService, roleDefaultProperties);
    }

    private ClientRegistration googleClientRegistrations(Oauth2LoginProperties.SocialProperties googleProperties) {

        return CommonOAuth2Provider.GOOGLE.getBuilder(Provider.GOOGLE.getProvider())
                .clientId(googleProperties.getClientId())
                .clientSecret(googleProperties.getClientSecret())
                .redirectUri("{baseUrl}/login/oauth2/code/{registrationId}")
                .build();
    }
}
