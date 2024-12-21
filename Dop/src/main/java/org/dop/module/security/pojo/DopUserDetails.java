package org.dop.module.security.pojo;

import lombok.RequiredArgsConstructor;
import org.dop.entity.state.UserPrimaryStatus;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

@RequiredArgsConstructor
public class DopUserDetails implements UserDetails {
    private final String identifier;
    private final String password;
    private final UserPrimaryStatus status;
    private final Collection<? extends GrantedAuthority> authorities;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return identifier;
    }

    @Override
    public boolean isAccountNonLocked() {
        return status != UserPrimaryStatus.BANNED;
    }

    @Override
    public boolean isEnabled() {
        return status == UserPrimaryStatus.ENABLED;
    }
}
