package org.dop.config.property;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Component
@ConfigurationProperties(prefix = "dop.oauth2.authorization.server")
public class Oauth2AuthorizationServerProperties {

    /// Endpoint authorization server
    private String authorizationEndpoint = "/oauth2/authorize";
    private String deviceAuthorizationEndpoint = "/oauth2/device-authorization";
    private String deviceVerificationEndpoint = "/oauth2/device-verification";
    private String tokenEndpoint = "/oauth2/token";
    private String tokenIntrospectionEndpoint = "/oauth2/introspect";
    private String tokenRevocationEndpoint = "/oauth2/revoke";
    private String jwkSetEndpoint = "/oauth2/jwks";
    private String oidcLogoutEndpoint = "/connect/logout";
    private String oidcUserInfoEndpoint = "/connect/userinfo";
    private String oidcClientRegistrationEndpoint = "/connect/register";
    private String consentPageEndpoint = "/oauth2/consent";
}
