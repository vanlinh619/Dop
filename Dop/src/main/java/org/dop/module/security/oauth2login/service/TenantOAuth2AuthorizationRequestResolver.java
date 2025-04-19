package org.dop.module.security.oauth2login.service;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.dop.module.tenant.TenantExtractService;
import org.springframework.security.oauth2.client.web.DefaultOAuth2AuthorizationRequestResolver;
import org.springframework.security.oauth2.client.web.OAuth2AuthorizationRequestResolver;
import org.springframework.security.oauth2.core.endpoint.OAuth2AuthorizationRequest;

@RequiredArgsConstructor
public class TenantOAuth2AuthorizationRequestResolver implements OAuth2AuthorizationRequestResolver {

    public static final String TENANT_DELIMITER = "*tenant*";
    private final DefaultOAuth2AuthorizationRequestResolver resolver;
    private final TenantExtractService tenantExtractService;


    @Override
    public OAuth2AuthorizationRequest resolve(HttpServletRequest request) {
        OAuth2AuthorizationRequest authorizationRequest = resolver.resolve(request);
        if (authorizationRequest == null) {
            return null;
        }
        String tenant = tenantExtractService.extractTenant(request);

        return OAuth2AuthorizationRequest.from(authorizationRequest)
                .redirectUri(resolveRedirectUri(authorizationRequest, tenant))
                .build();
    }

    @Override
    public OAuth2AuthorizationRequest resolve(HttpServletRequest request, String clientRegistrationId) {

        OAuth2AuthorizationRequest authorizationRequest = resolver.resolve(request, clientRegistrationId);
        if (authorizationRequest == null) {
            return null;
        }
        String tenant = tenantExtractService.extractTenant(request);

        return OAuth2AuthorizationRequest.from(authorizationRequest)
                .redirectUri(resolveRedirectUri(authorizationRequest, tenant))
                .build();
    }

    private String resolveRedirectUri(OAuth2AuthorizationRequest original, String tenant) {
        String template = original.getRedirectUri();
        return template.replace(TENANT_DELIMITER, tenant);
    }
}
