package org.dop.module.security.service;

import lombok.RequiredArgsConstructor;
import org.dop.config.property.RoleDefaultProperties;
import org.dop.entity.state.UserPrimaryStatus;
import org.dop.module.manageuser.pojo.data.UserJitData;
import org.dop.module.manageuser.pojo.projection.Auth2UserAuthenticatedProjection;
import org.dop.module.manageuser.service.Oauth2UserInfoService;
import org.dop.module.security.pojo.data.DopOidcUser;
import org.dop.module.security.pojo.data.UserAuthenticatedData;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserRequest;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;

import java.util.Collection;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class DopOidcUserService extends OidcUserService {

    private final Oauth2UserInfoService oauth2UserInfoService;
    private final RoleDefaultProperties roleDefaultProperties;

    @Override
    public OidcUser loadUser(OidcUserRequest userRequest) throws OAuth2AuthenticationException {

        // 1) Fetch the authority information from the protected resource using accessToken
        OidcUser oidcUser = super.loadUser(userRequest);
        String registrationId = userRequest.getClientRegistration().getRegistrationId();

        // 2) Map the authority information to one or more GrantedAuthority's and add it to mappedAuthorities
        UserAuthenticatedData userAuthenticated = findAuthenticatedUser(oidcUser, registrationId);
        Collection<? extends GrantedAuthority> mappedAuthorities = mappingAuthorities(userAuthenticated.getRoles());


        // 3) Create a copy of oidcUser but use the mappedAuthorities instead
        return new DopOidcUser(mappedAuthorities, oidcUser.getIdToken(), oidcUser.getUserInfo(), userAuthenticated.getId().toString());
    }

    private UserAuthenticatedData findAuthenticatedUser(OidcUser oidcUser, String registrationId) {
        String subject = oidcUser.getSubject();
        Optional<Auth2UserAuthenticatedProjection> userCredentialOptional = oauth2UserInfoService.findAuth2UserAuthenticated(subject);

        if (userCredentialOptional.isPresent()) {
            // User exists
            Auth2UserAuthenticatedProjection userCredential = userCredentialOptional.get();
            if (userCredential.status() == UserPrimaryStatus.ENABLED) {
                return new UserAuthenticatedData(userCredential.id(), oauth2UserInfoService.findRoles(userCredential.id()));
            } else {
                return new UserAuthenticatedData(userCredential.id(), Set.of());
            }
        } else {
            // User does not exist
            UserJitData userJitData = oauth2UserInfoService.persistUserOidc(oidcUser, registrationId);
            return new UserAuthenticatedData(userJitData.getId(), userJitData.getRoles());
        }
    }

    private Set<GrantedAuthority> mappingAuthorities(Collection<String> roles) {
        return roles.stream()
                .map(role -> new SimpleGrantedAuthority(roleDefaultProperties.addPrefix(role)))
                .collect(Collectors.toSet());
    }

}
