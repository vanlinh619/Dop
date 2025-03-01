package org.dop.module.security.login.service;

import lombok.RequiredArgsConstructor;
import org.dop.config.property.RoleDefaultProperties;
import org.dop.entity.state.UserPrimaryStatus;
import org.dop.module.user.pojo.projection.UserAuthenticatedProjection;
import org.dop.module.user.service.UserInfoService;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
@RequiredArgsConstructor
public class UserPrimaryDetailService implements UserDetailsService {

    private final UserInfoService userInfoService;
    private final RoleDefaultProperties roleDefaultProperties;

    @Override
    public UserDetails loadUserByUsername(String identifier) throws UsernameNotFoundException {
        UserAuthenticatedProjection userAuthority = userInfoService.findUserCredential(identifier)
                .orElseThrow(() -> new UsernameNotFoundException(String.format("User %s not found", identifier)));

        Collection<? extends GrantedAuthority> authorities = userInfoService.findRoles(userAuthority.id()).stream()
                .map(role -> new SimpleGrantedAuthority(roleDefaultProperties.addPrefix(role)))
                .toList();
        boolean enabled = userAuthority.status() == UserPrimaryStatus.ENABLED;
        return new User(
                userAuthority.id().toString(),
                userAuthority.password(),
                enabled,
                true,
                true,
                true,
                authorities
        );
    }
}
