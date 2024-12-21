package org.dop.module.startup.clientrole;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.dop.config.property.ClientMasterProperties;
import org.dop.entity.ClientRole;
import org.dop.entity.Oauth2RegisteredClient;
import org.dop.entity.state.StartupName;
import org.dop.module.startup.Starter;
import org.dop.repository.ClientRoleRepository;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service(StartupName.CLIENT_ROLE)
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
@RequiredArgsConstructor
public class ClientMasterRoleStarter implements Starter {

    private final EntityManager entityManager;
    private final ClientMasterProperties clientMasterProperties;
    private final RegisteredClientRepository registeredClientRepository;
    private final ClientRoleRepository clientRoleRepository;
    private final @Qualifier(StartupName.REGISTERED_CLIENT) Starter oauth2RegisteredClientStarter;

    @Override
    public void start() {

        RegisteredClient oidcClient = registeredClientRepository.findByClientId(clientMasterProperties.getClientId());
        if (oidcClient == null) {
            throw new EntityNotFoundException("Oidc client not found.");
        }
        Oauth2RegisteredClient client = entityManager.getReference(Oauth2RegisteredClient.class, oidcClient.getId());
        List<ClientRole> clientRoles = List.of(
                ClientRole.builder()
                        .name(clientMasterProperties.getRoleMaster())
                        .client(client)
                        .build(),
                ClientRole.builder()
                        .name(clientMasterProperties.getRoleAdmin())
                        .client(client)
                        .build(),
                ClientRole.builder()
                        .name(clientMasterProperties.getRoleUser())
                        .client(client)
                        .build()
        );
        clientRoleRepository.saveAll(clientRoles);
    }

    @Override
    public int priority() {
        return oauth2RegisteredClientStarter.priority() + 1;
    }
}
