package org.dop.module.security.authorizationserver.service;

import org.springframework.security.oauth2.core.oidc.OidcUserInfo;
import org.springframework.security.oauth2.server.authorization.oidc.authentication.OidcUserInfoAuthenticationContext;

import java.util.function.Function;

public interface UserInfoEndpointService {
    Function<OidcUserInfoAuthenticationContext, OidcUserInfo> getUserInfoMapper();
}
