package org.dop.module.security.service;

import lombok.RequiredArgsConstructor;
import org.dop.config.SecurityConfig;
import org.dop.module.security.pojo.DopUserDetails;
import org.dop.module.user.pojo.projection.UserAuthorityProjection;
import org.dop.module.user.service.UserPrimaryService;
import org.dop.repository.UserPrimaryRoleRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserPrimaryDetailService implements UserDetailsService {

    private final UserPrimaryService userPrimaryService;
    private final UserPrimaryRoleRepository userPrimaryRoleRepository;

    @Override
    public UserDetails loadUserByUsername(String identifier) throws UsernameNotFoundException {
        UserAuthorityProjection userAuthority = userPrimaryService.findUserAuthority(identifier)
                .orElseThrow(() -> new UsernameNotFoundException(String.format("User %s not found", identifier)));
        Collection<? extends GrantedAuthority> roles = userPrimaryRoleRepository.findRoles(userAuthority.id()).stream()
                .map((role) -> new SimpleGrantedAuthority(SecurityConfig.ROLE_PREFIX + role))
                .collect(Collectors.toList());
        return new DopUserDetails(identifier, userAuthority.password(), userAuthority.status(), roles);
    }
}
