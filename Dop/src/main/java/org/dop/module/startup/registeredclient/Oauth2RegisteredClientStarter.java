package org.dop.module.startup.registeredclient;

import lombok.RequiredArgsConstructor;
import org.dop.config.property.ClientMasterProperties;
import org.dop.entity.state.StartupName;
import org.dop.module.startup.Starter;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.settings.ClientSettings;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service(StartupName.REGISTERED_CLIENT)
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
@RequiredArgsConstructor
public class Oauth2RegisteredClientStarter implements Starter {

    private final RegisteredClientRepository registeredClientRepository;
    private final ClientMasterProperties clientMasterProperties;

    /**
     * Create default public client, that can use by Single Page Application
     */
    @Transactional
    @Override
    public void start() {
        RegisteredClient oidcClient = RegisteredClient.withId(UUID.randomUUID().toString())
                .clientId(clientMasterProperties.getClientId())
                .clientAuthenticationMethod(ClientAuthenticationMethod.NONE)
                .authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE)
                .scope(clientMasterProperties.getScopeMaster())
                .redirectUri(clientMasterProperties.getRedirectUrl())
                .clientSettings(ClientSettings.builder()
                        .requireProofKey(true)
                        .build())
                .build();

        registeredClientRepository.save(oidcClient);
    }
}
