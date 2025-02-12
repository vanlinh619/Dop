package org.dop.module.security.login.service;

import lombok.RequiredArgsConstructor;
import org.dop.config.property.RoleDefaultProperties;
import org.dop.module.security.login.pojo.UserAccess;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {

    private final RoleDefaultProperties roleDefaultProperties;


    @Override
    public UserAccess getUserAccess() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        List<String> roles = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .filter(authority -> authority.startsWith(roleDefaultProperties.getRolePrefix()))
                .map(authority -> authority.substring(roleDefaultProperties.getRolePrefix().length()))
                .toList();
        return new UserAccess(authentication.getName(), roles);
    }
}
