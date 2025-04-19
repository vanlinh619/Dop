package org.dop.config;

import org.dop.config.property.Oauth2LoginProperties;
import org.dop.config.property.RoleDefaultProperties;
import org.dop.entity.state.Provider;
import org.dop.module.security.oauth2login.service.DopOidcUserService;
import org.dop.module.security.oauth2login.service.TenantOAuth2AuthorizationRequestResolver;
import org.dop.module.tenant.TenantExtractService;
import org.dop.module.user.service.UserInfoService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.oauth2.client.CommonOAuth2Provider;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserService;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.registration.InMemoryClientRegistrationRepository;
import org.springframework.security.oauth2.client.web.DefaultOAuth2AuthorizationRequestResolver;
import org.springframework.security.oauth2.client.web.OAuth2AuthorizationRequestResolver;

import java.util.function.Supplier;

@Configuration
public class Oauth2LoginConfig {

    private ClientRegistration googleClientRegistration;
    private ClientRegistrationRepository clientRegistrationRepository;
    private OidcUserService oidcUserService;

    @Bean
    public Supplier<ClientRegistration> googleClientRegistrations(Oauth2LoginProperties oauth2LoginProperties) {
        return () -> {
            if (oauth2LoginProperties.isSocialEnable(Provider.GOOGLE)) {
                if (googleClientRegistration == null) {
                    Oauth2LoginProperties.SocialProperties googleProperties = oauth2LoginProperties.getSocials()
                            .get(Provider.GOOGLE.getProvider());
                    googleClientRegistration = CommonOAuth2Provider.GOOGLE.getBuilder(Provider.GOOGLE.getProvider())
                            .clientId(googleProperties.getClientId())
                            .clientSecret(googleProperties.getClientSecret())
                            .redirectUri(String.format(
                                    "{baseUrl}/%s/login/oauth2/code/{registrationId}",
                                    TenantOAuth2AuthorizationRequestResolver.TENANT_DELIMITER
                            ))
                            .build();
                }
                return googleClientRegistration;
            }
            return null;
        };
    }

    @Bean
    public Supplier<ClientRegistrationRepository> clientRegistrationRepository(
            Oauth2LoginProperties oauth2LoginProperties,
            Supplier<ClientRegistration> googleClientRegistrations
    ) {
        return () -> {
            if (oauth2LoginProperties.anySocialEnable()) {
                if (clientRegistrationRepository == null) {
                    clientRegistrationRepository = new InMemoryClientRegistrationRepository(
                            googleClientRegistrations.get()
                    );
                }
                return clientRegistrationRepository;
            }
            return null;
        };
    }

    @Bean
    public Supplier<OidcUserService> dopOidcUserService(
            Oauth2LoginProperties oauth2LoginProperties,
            UserInfoService userInfoService,
            RoleDefaultProperties roleDefaultProperties
    ) {
        return () -> {
            if (oauth2LoginProperties.isSocialEnable(Provider.GOOGLE)) {
                if (oidcUserService == null) {
                    oidcUserService = new DopOidcUserService(userInfoService, roleDefaultProperties);
                }
                return oidcUserService;
            }
            return null;
        };
    }

    @Bean
    public Supplier<OAuth2AuthorizationRequestResolver> authorizationRequestResolver(
            Oauth2LoginProperties oauth2LoginProperties,
            Supplier<ClientRegistrationRepository> clientRegistrationRepositorySupplier,
            TenantExtractService tenantExtractService
    ) {
        DefaultOAuth2AuthorizationRequestResolver resolver = new DefaultOAuth2AuthorizationRequestResolver(
                clientRegistrationRepositorySupplier.get(),
                oauth2LoginProperties.getAuthorizationEndpoint()
        );
        return () -> {
            if (oauth2LoginProperties.anySocialEnable()) {
                return new TenantOAuth2AuthorizationRequestResolver(resolver, tenantExtractService);
            }
            return null;
        };
    }
}
