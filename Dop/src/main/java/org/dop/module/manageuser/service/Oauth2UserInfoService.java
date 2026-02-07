package org.dop.module.manageuser.service;

import org.dop.module.manageuser.pojo.data.UserJitData;
import org.dop.module.manageuser.pojo.projection.*;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;

import java.util.Optional;
import java.util.Set;
import java.util.UUID;

public interface Oauth2UserInfoService {

    UserJitData persistUserOidc(OidcUser oidcUser, String registrationId);

    /**
     * Get user info for consent page
     */
    UserConsentProjection getUserConsentInfo(String uuid);

    Optional<UserAuthenticatedProjection> findUserCredential(String identifier);

    Set<String> findRoles(UUID userId);

    Optional<Auth2UserAuthenticatedProjection> findAuth2UserAuthenticated(String subject);

    /**
     * Get user info for authorization server
     */
    ProfileUserInfoProjection getProfileInfo(String identifier);

    EmailUserInfoProjection getEmailInfo(String identifier);

    AddressUserInfoProjection getAddressInfo(String identifier);

    PhoneUserInfoProjection getPhoneInfo(String identifier);

}
