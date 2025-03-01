package org.dop.module.role.service;

import jakarta.annotation.Nullable;
import lombok.RequiredArgsConstructor;
import org.dop.repository.RoleRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;


    @Override
    public Set<String> verifyRole(@Nullable List<String> roles) {
        if (roles == null || roles.isEmpty()) {
            return Set.of();
        }
        return roleRepository.verifyRole(roles);
    }
}
