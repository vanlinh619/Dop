package org.dop.module.user.service;

import org.dop.module.user.pojo.data.UserJitData;
import org.dop.module.user.pojo.projection.*;
import org.dop.module.user.pojo.request.UserInfoRequest;
import org.dop.module.user.pojo.request.UserPageRequest;
import org.dop.module.user.pojo.response.UserInfoResponse;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

public interface UserInfoService {
    /**
     * Create user primary by super account.
     */
    UserInfoResponse createUserPrimary(UserInfoRequest userInfoRequest);

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

    List<UserInfoResponse> listUserPage(UserPageRequest userPageRequest);
}
