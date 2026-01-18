package org.dop.config.security;

import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.proc.SecurityContext;
import org.dop.config.property.Oauth2AuthorizationServerProperties;
import org.dop.config.property.RoleDefaultProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.config.annotation.web.configuration.OAuth2AuthorizationServerConfiguration;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.server.authorization.*;
import org.springframework.security.oauth2.server.authorization.client.JdbcRegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.settings.AuthorizationServerSettings;
import org.springframework.security.oauth2.server.authorization.token.JwtEncodingContext;
import org.springframework.security.oauth2.server.authorization.token.OAuth2TokenCustomizer;

import java.util.Collections;
import java.util.Set;
import java.util.stream.Collectors;

@Configuration
public class Oauth2AuthorizationServerConfig {

    /**
     * Customize the JWT token to include the roles and scopes of the principal
     */
    @Bean
    public OAuth2TokenCustomizer<JwtEncodingContext> jwtTokenCustomizer(RoleDefaultProperties roleDefaultProperties) {
        return (context) -> {
            if (OAuth2TokenType.ACCESS_TOKEN.equals(context.getTokenType())) {
                context.getClaims().claims((claims) -> {
                    Set<String> roles = AuthorityUtils.authorityListToSet(context.getPrincipal().getAuthorities())
                            .stream()
                            .filter(authority -> authority.startsWith(roleDefaultProperties.getRolePrefix()))
                            .map(authority -> authority.substring(roleDefaultProperties.getRolePrefix().length()))
                            .collect(Collectors.collectingAndThen(Collectors.toSet(), Collections::unmodifiableSet));
                    claims.put(roleDefaultProperties.getRoleClaim(), roles);
                });
            }
        };
    }

    /**
     * Register an {@link RegisteredClientRepository} with a default {@link RegisteredClient} to test
     *
     * @return {@link RegisteredClientRepository}
     */
    @Bean
    public RegisteredClientRepository registeredClientRepository(JdbcTemplate jdbcTemplate) {
        return new JdbcRegisteredClientRepository(jdbcTemplate);
    }

    @Bean
    public OAuth2AuthorizationService authorizationService(JdbcTemplate jdbcTemplate, RegisteredClientRepository registeredClientRepository) {
        return new JdbcOAuth2AuthorizationService(jdbcTemplate, registeredClientRepository);
    }

    @Bean
    public OAuth2AuthorizationConsentService authorizationConsentService(JdbcTemplate jdbcTemplate, RegisteredClientRepository registeredClientRepository) {
        return new JdbcOAuth2AuthorizationConsentService(jdbcTemplate, registeredClientRepository);
    }

    @Bean
    public JwtDecoder jwtDecoder(JWKSource<SecurityContext> jwkSource) {
        return OAuth2AuthorizationServerConfiguration.jwtDecoder(jwkSource);
    }

    @Bean
    public AuthorizationServerSettings authorizationServerSettings(Oauth2AuthorizationServerProperties properties) {
        return AuthorizationServerSettings.builder()
                .multipleIssuersAllowed(false) // only support single tenant
                .authorizationEndpoint(properties.getAuthorizationEndpoint())
                .deviceAuthorizationEndpoint(properties.getDeviceAuthorizationEndpoint())
                .deviceVerificationEndpoint(properties.getDeviceVerificationEndpoint())
                .tokenEndpoint(properties.getTokenEndpoint())
                .tokenIntrospectionEndpoint(properties.getTokenIntrospectionEndpoint())
                .tokenRevocationEndpoint(properties.getTokenRevocationEndpoint())
                .jwkSetEndpoint(properties.getJwkSetEndpoint())
                .oidcLogoutEndpoint(properties.getOidcLogoutEndpoint())
                .oidcUserInfoEndpoint(properties.getOidcUserInfoEndpoint())
                .oidcClientRegistrationEndpoint(properties.getOidcClientRegistrationEndpoint())
                .build();
    }
}
