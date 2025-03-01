package org.dop.module.user.service;

import org.dop.module.user.pojo.data.UserJitData;
import org.dop.module.user.pojo.projection.Auth2UserAuthenticatedProjection;
import org.dop.module.user.pojo.projection.UserAuthenticatedProjection;
import org.dop.module.user.pojo.request.UserInfoRequest;
import org.dop.module.user.pojo.projection.UserConsentProjection;
import org.dop.module.user.pojo.response.UserInfoResponse;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;

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
}
