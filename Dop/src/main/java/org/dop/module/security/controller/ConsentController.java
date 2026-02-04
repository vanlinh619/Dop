package org.dop.module.security.controller;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.dop.config.security.Oauth2ResourceServerConfig;
import org.dop.module.manageuser.pojo.projection.UserConsentProjection;
import org.dop.module.manageuser.service.Oauth2UserInfoService;
import org.dop.module.security.service.ConsentService;
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

import java.util.Collections;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;

@Controller
@PreAuthorize("isAuthenticated()")
@RequiredArgsConstructor
@RequestMapping("oauth2/consent")
public class ConsentController {

    private final RegisteredClientRepository registeredClientRepository;
    private final OAuth2AuthorizationConsentService authorizationConsentService;
    private final Oauth2UserInfoService oauth2UserInfoService;
    private final ConsentService consentService;


    @GetMapping
    public String consent(
            @RequestParam(OAuth2ParameterNames.CLIENT_ID) String clientId,
            @RequestParam(OAuth2ParameterNames.SCOPE) String scope,
            @RequestParam(OAuth2ParameterNames.STATE) String state,
            Authentication authentication,
            Locale locale,
            Model model
    ) {

        RegisteredClient registeredClient = registeredClientRepository.findByClientId(clientId);
        if (registeredClient == null) {
            throw new EntityNotFoundException("Registered client master not found.");
        }

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
        UserConsentProjection userConsent = oauth2UserInfoService.getUserConsentInfo(uuid);

        model.addAttribute("clientName", registeredClient.getClientName());
        model.addAttribute("clientId", clientId);
        model.addAttribute("state", state);
        model.addAttribute("needApproveScopes", consentService.withDescription(needApproveScopes, locale));
        model.addAttribute("approvedScopes", consentService.withDescription(approvedScopes, locale));
        model.addAttribute("userConsent", userConsent);

        return "consent";
    }
}
