package org.dop.module.security.authorizationserver.service;

import lombok.RequiredArgsConstructor;
import org.dop.module.user.pojo.projection.AddressUserInfoProjection;
import org.dop.module.user.pojo.projection.EmailUserInfoProjection;
import org.dop.module.user.pojo.projection.PhoneUserInfoProjection;
import org.dop.module.user.pojo.projection.ProfileUserInfoProjection;
import org.dop.module.user.service.UserInfoService;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.core.oidc.OidcScopes;
import org.springframework.security.oauth2.core.oidc.OidcUserInfo;
import org.springframework.security.oauth2.server.authorization.oidc.authentication.OidcUserInfoAuthenticationContext;
import org.springframework.security.oauth2.server.authorization.oidc.authentication.OidcUserInfoAuthenticationToken;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.function.Function;


@Service
@RequiredArgsConstructor
public class UserInfoEndpointServiceImpl implements UserInfoEndpointService {

    private final UserInfoService userInfoService;


    @Override
    public Function<OidcUserInfoAuthenticationContext, OidcUserInfo> getUserInfoMapper() {
        return context -> {

            OidcUserInfoAuthenticationToken authentication = context.getAuthentication();
            JwtAuthenticationToken principal = (JwtAuthenticationToken) authentication.getPrincipal();
            String identifier = principal.getName();

            Collection<? extends GrantedAuthority> authorities = principal.getAuthorities();
            List<String> scopes = authorities.stream()
                    .map(GrantedAuthority::getAuthority)
                    .toList();

            /// Create user info builder
            OidcUserInfo.Builder builder = OidcUserInfo.builder()
                    .subject(identifier);

            if (scopes.contains(OidcScopes.PROFILE)) {
                ProfileUserInfoProjection profileUserInfo = userInfoService.getProfileInfo(identifier);
                builder
                        .name(profileUserInfo.fullName())
                        .familyName(profileUserInfo.familyName())
                        .middleName(profileUserInfo.middleName())
                        .givenName(profileUserInfo.givenName())
                        .picture(profileUserInfo.picture())
                        .gender(profileUserInfo.gender() != null ? profileUserInfo.gender().name() : null)
                        .birthdate(profileUserInfo.birthDate() != null ? profileUserInfo.birthDate().toString() : null)
                        .profile(profileUserInfo.profile())
                        .website(profileUserInfo.website())
                        .locale(profileUserInfo.locale() != null ? profileUserInfo.locale().toString() : null)
                        .zoneinfo(profileUserInfo.zoneInfo() != null ? profileUserInfo.zoneInfo().toString() : null)
                        .updatedAt(profileUserInfo.lastModifiedDate() != null ? profileUserInfo.lastModifiedDate().toString() : null);
            }

            if (scopes.contains(OidcScopes.EMAIL)) {
                EmailUserInfoProjection emailUserInfo = userInfoService.getEmailInfo(identifier);
                builder
                        .email(emailUserInfo.email())
                        .emailVerified(emailUserInfo.verified());
            }

            if (scopes.contains(OidcScopes.ADDRESS)) {
                AddressUserInfoProjection addressUserInfo = userInfoService.getAddressInfo(identifier);
                builder
                        .address(addressUserInfo.address());
            }

            if (scopes.contains(OidcScopes.PHONE)) {
                PhoneUserInfoProjection profileUserInfo = userInfoService.getPhoneInfo(identifier);
                builder
                        .phoneNumber(profileUserInfo.phone())
                        .phoneNumberVerified(profileUserInfo.verified());
            }

            return builder.build();
        };
    }
}
