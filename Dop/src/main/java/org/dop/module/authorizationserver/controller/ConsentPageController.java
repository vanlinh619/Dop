package org.dop.module.authorizationserver.controller;

import lombok.RequiredArgsConstructor;
import org.dop.config.Oauth2ResourceServerConfig;
import org.dop.module.user.pojo.projection.UserConsentProjection;
import org.dop.module.user.service.UserInfoService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.endpoint.OAuth2ParameterNames;
import org.springframework.security.oauth2.server.authorization.OAuth2AuthorizationConsent;
import org.springframework.security.oauth2.server.authorization.OAuth2AuthorizationConsentService;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.*;

@Controller
@PreAuthorize("isAuthenticated()")
@RequiredArgsConstructor
@RequestMapping("/oauth2/consent")
public class ConsentPageController {

    private final RegisteredClientRepository registeredClientRepository;
    private final OAuth2AuthorizationConsentService authorizationConsentService;
    private final UserInfoService userInfoService;

    @GetMapping
    public String consent(
            @RequestParam(OAuth2ParameterNames.CLIENT_ID) String clientId,
            @RequestParam(OAuth2ParameterNames.SCOPE) String scope,
            @RequestParam(OAuth2ParameterNames.STATE) String state,
            Authentication authentication,
            Model model
    ) {

        RegisteredClient registeredClient = registeredClientRepository.findByClientId(clientId);
        assert registeredClient != null;

        OAuth2AuthorizationConsent currentAuthorizationConsent = authorizationConsentService.findById(registeredClient.getId(), authentication.getName());
        Set<String> authorizedScopes = currentAuthorizationConsent != null
            ? currentAuthorizationConsent.getScopes()
            : Collections.emptySet();

        // Remove scopes that were already approved
        Set<String> needApproveScopes = new HashSet<>();
        Set<String> approvedScopes = new HashSet<>();
        for (String requestedScope : StringUtils.delimitedListToStringArray(scope, Oauth2ResourceServerConfig.DEFAULT_AUTHORITIES_CLAIM_DELIMITER)) {
            if (authorizedScopes.contains(requestedScope)) {
                approvedScopes.add(requestedScope);
            } else {
                needApproveScopes.add(requestedScope);
            }
        }

        String uuid = authentication.getName();
        UserConsentProjection userConsent = userInfoService.getUserConsentInfo(uuid);

        model.addAttribute("clientName", registeredClient.getClientName());
        model.addAttribute("clientId", clientId);
        model.addAttribute("state", state);
        model.addAttribute("needApproveScopes", withDescription(needApproveScopes));
        model.addAttribute("approvedScopes", withDescription(approvedScopes));
        model.addAttribute("userConsent", userConsent);

        return "consent";
    }

    private static Set<ScopeWithDescription> withDescription(Set<String> scopes) {
        Set<ScopeWithDescription> scopeWithDescriptions = new HashSet<>();
        for (String scope : scopes) {
            scopeWithDescriptions.add(new ScopeWithDescription(scope));

        }
        return scopeWithDescriptions;
    }

    public static class ScopeWithDescription {
        private static final String DEFAULT_DESCRIPTION = "UNKNOWN SCOPE - We cannot provide information about this permission, use caution when granting this.";
        private static final Map<String, String> scopeDescriptions = new HashMap<>();
        static {
            scopeDescriptions.put(
                    "message.read",
                    "This application will be able to read your message."
            );
            scopeDescriptions.put(
                    "message.write",
                    "This application will be able to add new messages. It will also be able to edit and delete existing messages."
            );
            scopeDescriptions.put(
                    "other.scope",
                    "This is another scope example of a scope description."
            );
        }

        public final String scope;
        public final String description;

        ScopeWithDescription(String scope) {
            this.scope = scope;
            this.description = scopeDescriptions.getOrDefault(scope, DEFAULT_DESCRIPTION);
        }
    }
}
