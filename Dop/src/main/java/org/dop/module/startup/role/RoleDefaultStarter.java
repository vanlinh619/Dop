package org.dop.module.startup.role;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.dop.config.property.RoleDefaultProperties;
import org.dop.config.property.UserAdminProperties;
import org.dop.entity.Role;
import org.dop.entity.UserPrimary;
import org.dop.entity.UserPrimaryRole;
import org.dop.entity.state.StartupName;
import org.dop.module.startup.Starter;
import org.dop.repository.RoleRepository;
import org.dop.repository.UserPrimaryRepository;
import org.dop.repository.UserPrimaryRoleRepository;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service(StartupName.ROLE_DEFAULT)
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
@RequiredArgsConstructor
public class RoleDefaultStarter implements Starter {

    private final RoleDefaultProperties roleDefaultProperties;
    private final UserAdminProperties userAdminProperties;

    private final EntityManager entityManager;
    private final UserPrimaryRepository userPrimaryRepository;
    private final UserPrimaryRoleRepository userPrimaryRoleRepository;
    private final RoleRepository roleRepository;

    private final @Qualifier(StartupName.ADMIN_USER) Starter adminUserStarter;

    @Transactional
    @Override
    public void start() {

        Role roleAdmin = Role.builder()
                .name(roleDefaultProperties.getRoleAdmin())
                .build();
        List<Role> roles = List.of(
                roleAdmin,
                Role.builder()
                        .name(roleDefaultProperties.getRoleUser())
                        .build()
        );
        roleRepository.saveAll(roles);

        /// Add role
        UUID id = userPrimaryRepository.findUserIdByIdentifier(userAdminProperties.getUsername())
                .orElseThrow(() -> new EntityNotFoundException(String.format("User %s not found", userAdminProperties.getUsername())));
        UserPrimaryRole userPrimaryRole = UserPrimaryRole.builder()
                .user(entityManager.getReference(UserPrimary.class, id))
                .role(roleAdmin)
                .build();
        userPrimaryRoleRepository.save(userPrimaryRole);
    }

    @Override
    public int priority() {
        return adminUserStarter.priority() + 1;
    }
}
