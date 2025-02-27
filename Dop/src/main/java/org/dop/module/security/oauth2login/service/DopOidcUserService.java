package org.dop.module.security.oauth2login.service;

import lombok.RequiredArgsConstructor;
import org.dop.entity.state.UserPrimaryStatus;
import org.dop.module.user.pojo.data.UserJitData;
import org.dop.module.user.pojo.projection.Auth2UserCredentialProjection;
import org.dop.module.user.service.UserInfoService;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserRequest;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserService;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.util.StringUtils;

import java.util.*;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class DopOidcUserService extends OidcUserService {

    private final UserInfoService userInfoService;

    @Override
    public OidcUser loadUser(OidcUserRequest userRequest) throws OAuth2AuthenticationException {

        /// 1) Fetch the authority information from the protected resource using accessToken
        OidcUser oidcUser = super.loadUser(userRequest);
        String registrationId = userRequest.getClientRegistration().getRegistrationId();

        /// 2) Map the authority information to one or more GrantedAuthority's and add it to mappedAuthorities
        Collection<? extends GrantedAuthority> mappedAuthorities = findAuthorities(oidcUser, registrationId);


        /// 3) Create a copy of oidcUser but use the mappedAuthorities instead
        ClientRegistration.ProviderDetails providerDetails = userRequest.getClientRegistration().getProviderDetails();
        String userNameAttributeName = providerDetails.getUserInfoEndpoint().getUserNameAttributeName();
        if (StringUtils.hasText(userNameAttributeName)) {
            oidcUser = new DefaultOidcUser(mappedAuthorities, oidcUser.getIdToken(), oidcUser.getUserInfo(), userNameAttributeName);
        } else {
            oidcUser = new DefaultOidcUser(mappedAuthorities, oidcUser.getIdToken(), oidcUser.getUserInfo());
        }

        return oidcUser;
    }

    private Set<GrantedAuthority> findAuthorities(OidcUser oidcUser, String registrationId) {
        String subject = oidcUser.getSubject();
        Optional<Auth2UserCredentialProjection> userCredentialOptional = userInfoService.findAuth2UserCredential(subject);

        if (userCredentialOptional.isPresent()) {
            /// User exists
            Auth2UserCredentialProjection userCredential = userCredentialOptional.get();
            if (userCredential.status() == UserPrimaryStatus.ENABLED) {
                return mappingAuthorities(userInfoService.findRoles(userCredential.id()));
            } else {
                return Set.of();
            }
        } else {
            /// User does not exist
            UserJitData userJitData = userInfoService.persistUserOidc(oidcUser, registrationId);
            return mappingAuthorities(userJitData.getRoles());
        }
    }

    private Set<GrantedAuthority> mappingAuthorities(Collection<String> roles) {
        return roles.stream()
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toSet());
    }

}
