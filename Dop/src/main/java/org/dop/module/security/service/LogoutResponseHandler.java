//package org.dop.module.security.service;
//
//import jakarta.servlet.ServletException;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//import lombok.RequiredArgsConstructor;
//import org.dop.config.property.Oauth2AuthorizationServerProperties;
//import org.dop.config.property.SecurityProperties;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
//import org.springframework.stereotype.Service;
//
//import java.io.IOException;
//
//@Service
//@RequiredArgsConstructor
//public class LogoutResponseHandler implements AuthenticationSuccessHandler {
//
//    private final SecurityProperties securityProperties;
//    private final Oauth2AuthorizationServerProperties oauth2AuthorizationServerProperties;
//
//    @Override
//    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
//        String redirectUri = request.getParameter(oauth2AuthorizationServerProperties.getPostLogoutRedirectUri());
//
//        if (redirectUri == null) {
//            response.sendRedirect(securityProperties.getLoginUrl());
//            return;
//        }
//
//        response.sendRedirect(redirectUri);
//    }
//}
