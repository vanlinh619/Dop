package org.dop.module.security.login.service;

import lombok.RequiredArgsConstructor;
import org.dop.config.property.RoleDefaultProperties;
import org.dop.entity.state.UserPrimaryStatus;
import org.dop.module.user.pojo.projection.UserAuthenticatedProjection;
import org.dop.module.user.service.Oauth2UserInfoService;
import org.jspecify.annotations.NonNull;
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

    private final Oauth2UserInfoService oauth2UserInfoService;
    private final RoleDefaultProperties roleDefaultProperties;

    @Override
    public UserDetails loadUserByUsername(@NonNull String identifier) throws UsernameNotFoundException {
        UserAuthenticatedProjection userAuthority = oauth2UserInfoService.findUserCredential(identifier)
                .orElseThrow(() -> new UsernameNotFoundException(String.format("User %s not found", identifier)));

        Collection<? extends GrantedAuthority> authorities = oauth2UserInfoService.findRoles(userAuthority.id()).stream()
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
