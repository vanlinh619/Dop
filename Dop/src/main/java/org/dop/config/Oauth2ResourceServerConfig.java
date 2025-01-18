package org.dop.config;

import org.dop.config.property.RoleDefaultProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;

@Configuration
public class Oauth2ResourceServerConfig {
    private static final String DEFAULT_AUTHORITIES_CLAIM_DELIMITER = " ";
    private static final Collection<String> WELL_KNOWN_AUTHORITIES_CLAIM_NAMES = Arrays.asList("scope", "scp");

    /**
     * Convert JWT to Authentication include roles and scopes
     */
    @Bean
    public JwtAuthenticationConverter jwtAuthenticationConverter(
            RoleDefaultProperties roleDefaultProperties
    ) {
        Converter<Jwt, Collection<GrantedAuthority>> delegateConverter = jwt -> {
            /// Extract scope authorities from JWT
            Collection<GrantedAuthority> scopeAuthorities = new ArrayList<>();
            for (String scope : getAuthorities(jwt, getAuthoritiesClaimName(jwt))) {
                scopeAuthorities.add(new SimpleGrantedAuthority(scope));
            }
            /// Extract role authorities from JWT
            Collection<GrantedAuthority> roleAuthorities = new ArrayList<>();
            for (String authority : getAuthorities(jwt, roleDefaultProperties.getRoleClaim())) {
                roleAuthorities.add(new SimpleGrantedAuthority(roleDefaultProperties.addPrefix(authority)));
            }
            roleAuthorities.addAll(scopeAuthorities);
            return roleAuthorities;
        };

        JwtAuthenticationConverter jwtAuthenticationConverter = new JwtAuthenticationConverter();
        jwtAuthenticationConverter.setJwtGrantedAuthoritiesConverter(delegateConverter);
        return jwtAuthenticationConverter;
    }

    private String getAuthoritiesClaimName(Jwt jwt) {
        for (String claimName : WELL_KNOWN_AUTHORITIES_CLAIM_NAMES) {
            if (jwt.hasClaim(claimName)) {
                return claimName;
            }
        }
        return null;
    }

    private Collection<String> getAuthorities(Jwt jwt, String roleClaim) {
        Object authorities = jwt.getClaim(roleClaim);
        if (authorities instanceof String) {
            if (StringUtils.hasText((String) authorities)) {
                return Arrays.asList(((String) authorities).split(DEFAULT_AUTHORITIES_CLAIM_DELIMITER));
            }
            return Collections.emptyList();
        }
        if (authorities instanceof Collection) {
            return castAuthoritiesToCollection(authorities);
        }
        return Collections.emptyList();
    }

    @SuppressWarnings("unchecked")
    private Collection<String> castAuthoritiesToCollection(Object authorities) {
        return (Collection<String>) authorities;
    }
}
