package org.dop.module.startup.registeredclient;

import lombok.RequiredArgsConstructor;
import org.dop.config.property.ManagePageProperties;
import org.dop.entity.state.StartupName;
import org.dop.module.startup.Starter;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
import org.springframework.security.oauth2.core.oidc.OidcScopes;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.settings.ClientSettings;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service(StartupName.Fields.REGISTERED_CLIENT)
@RequiredArgsConstructor
public class Oauth2RegisteredClientStarter implements Starter {

    public static final String MASTER_CLIENT = "master-client";

    private final RegisteredClientRepository registeredClientRepository;
    private final ManagePageProperties managePageProperties;

    /**
     * Create default public client, that can use by Single Page Application
     */
    @Override
    public void start() {
        RegisteredClient oidcClient = RegisteredClient.withId(UUID.randomUUID().toString())
                .clientId(MASTER_CLIENT)
                .clientAuthenticationMethod(ClientAuthenticationMethod.NONE)
                .authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE)
                .authorizationGrantType(AuthorizationGrantType.REFRESH_TOKEN)
                .scope(OidcScopes.OPENID)
                .scope(OidcScopes.PROFILE)
                .redirectUri(managePageProperties.getRootUrl())
                .clientSettings(ClientSettings.builder()
                        .requireProofKey(true)
                        .build())
                .build();

        registeredClientRepository.save(oidcClient);
    }
}
