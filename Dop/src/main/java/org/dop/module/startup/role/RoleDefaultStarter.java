package org.dop.module.startup.role;

import lombok.RequiredArgsConstructor;
import org.dop.config.property.RoleDefaultProperties;
import org.dop.entity.Role;
import org.dop.entity.state.StartupName;
import org.dop.module.startup.Starter;
import org.dop.repository.RoleRepository;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service(StartupName.ROLE_DEFAULT)
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
@RequiredArgsConstructor
public class RoleDefaultStarter implements Starter {

    private final RoleDefaultProperties roleDefaultProperties;

    private final RoleRepository roleRepository;

    @Transactional
    @Override
    public void start() {
        List<Role> roles = List.of(
                Role.builder()
                        .name(roleDefaultProperties.getRoleAdmin())
                        .build(),
                Role.builder()
                        .name(roleDefaultProperties.getRoleUser())
                        .build()
        );
        roleRepository.saveAll(roles);
    }
}
