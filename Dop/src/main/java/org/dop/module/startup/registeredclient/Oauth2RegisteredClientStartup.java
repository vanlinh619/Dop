package org.dop.module.startup.registeredclient;

import lombok.RequiredArgsConstructor;
import org.dop.entity.state.StartupName;
import org.dop.module.startup.Starter;
import org.dop.repository.Oauth2RegisteredClientRepository;
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
public class Oauth2RegisteredClientStartup implements Starter {

    public static final String MASTER_CLIENT = "master-client";

    private final RegisteredClientRepository registeredClientRepository;

    @Override
    public void start() {
        RegisteredClient oidcClient = RegisteredClient.withId(UUID.randomUUID().toString())
                .clientId(MASTER_CLIENT)
                .clientAuthenticationMethod(ClientAuthenticationMethod.CLIENT_SECRET_BASIC)
                .authorizationGrantType(AuthorizationGrantType.CLIENT_CREDENTIALS)
                .authorizationGrantType(AuthorizationGrantType.REFRESH_TOKEN)
                .scope(OidcScopes.OPENID)
                .scope(OidcScopes.PROFILE)
                .scope(OidcScopes.EMAIL)
                .clientSettings(ClientSettings.builder().build())
                .build();

        registeredClientRepository.save(oidcClient);
    }
}
