package org.dop.module.security.service;

import lombok.RequiredArgsConstructor;
import org.dop.config.SecurityConfig;
import org.dop.module.security.pojo.DopUserDetails;
import org.dop.module.user.pojo.projection.UserAuthorityProjection;
import org.dop.module.user.service.UserPrimaryService;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
@RequiredArgsConstructor
public class UserPrimaryDetailService implements UserDetailsService {

    private final UserPrimaryService userPrimaryService;

    @Override
    public UserDetails loadUserByUsername(String identifier) throws UsernameNotFoundException {
        UserAuthorityProjection userAuthority = userPrimaryService.findUserAuthority(identifier)
                .orElseThrow(() -> new UsernameNotFoundException(String.format("User %s not found", identifier)));

        Collection<? extends GrantedAuthority> roles = userPrimaryService.findRoles(userAuthority.id()).stream()
                .map((role) -> new SimpleGrantedAuthority(SecurityConfig.ROLE_PREFIX + role))
                .toList();
        return new DopUserDetails(identifier, userAuthority.password(), userAuthority.status(), roles);
    }
}
