package org.dop.config;

import java.util.List;

import org.dop.config.property.Oauth2AuthorizationServerProperties;
import org.dop.config.property.SecurityRememberMeProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.MediaType;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.server.authorization.config.annotation.web.configurers.OAuth2AuthorizationServerConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;
import org.springframework.security.web.util.matcher.MediaTypeRequestMatcher;
import org.springframework.web.cors.CorsConfiguration;

@Configuration
@EnableMethodSecurity
@EnableWebSecurity
public class SecurityConfig {

    /**
     * Config authentication server
     */
    @Bean
    @Order(1)
    public SecurityFilterChain authorizationServerSecurityFilterChainCustom(
            HttpSecurity http,
            Oauth2AuthorizationServerProperties oauth2AuthorizationServerProperties
    ) throws Exception {
        OAuth2AuthorizationServerConfigurer authorizationServerConfigurer = OAuth2AuthorizationServerConfigurer.authorizationServer();

        http
                .securityMatcher(authorizationServerConfigurer.getEndpointsMatcher())
                .authorizeHttpRequests(authorize -> authorize
                        .anyRequest().authenticated()
                )
                .with(authorizationServerConfigurer, (authorizationServer) -> authorizationServer
                        /// Enable OpenID Connect 1.0
                        .oidc(Customizer.withDefaults())
                        .authorizationEndpoint(authorizationEndpoint -> authorizationEndpoint
                                .consentPage(oauth2AuthorizationServerProperties.getConsentPageEndpoint())
                        )
                )
                /// Redirect to the login page when not authenticated from the authorization endpoint
                .exceptionHandling((exceptions) -> exceptions
                        .defaultAuthenticationEntryPointFor(
                                new LoginUrlAuthenticationEntryPoint("/login"),
                                new MediaTypeRequestMatcher(MediaType.TEXT_HTML)
                        )
                );
        return http.build();
    }

    /**
     * Config login page
     */
    @Bean
    @Order(2)
    public SecurityFilterChain defaultSecurityFilterChain(
            HttpSecurity http,
            SecurityRememberMeProperties securityRememberMeProperties
    ) throws Exception {
        http
                .securityMatcher(
                        "/login"
                )
                .authorizeHttpRequests((authorize) -> authorize
                        .requestMatchers("/login").permitAll()
                        .anyRequest().denyAll()
                )
                .csrf(Customizer.withDefaults())
                .cors(corsConfigurer -> corsConfigurer
                        .configurationSource(request -> {
                            CorsConfiguration config = new CorsConfiguration();
                            config.setAllowedMethods(List.of("GET", "POST"));
                            config.setAllowedHeaders(List.of("*"));
                            return config;
                        })
                )
                .formLogin(formLoginConfigurer -> formLoginConfigurer
                        .loginPage("/login")
                        .usernameParameter("identifier")
                        .passwordParameter("password")
                );

        if (securityRememberMeProperties.isEnable()) {
            http.rememberMe(rememberMeConfigurer -> rememberMeConfigurer
                    .key(securityRememberMeProperties.getKey())
                    .rememberMeParameter("remember-me")
                    .tokenValiditySeconds(securityRememberMeProperties.getExpiresIn())
            );
        }

        return http.build();
    }

    /**
     * Config api manage page
     */
    @Bean
    @Order(3)
    public SecurityFilterChain securityFilterChainApi(HttpSecurity http) throws Exception {
        http
                .securityMatcher(
                        "/api/v1/**",
                        "/css/**",
                        "/js/**"
                )
                .authorizeHttpRequests((authorize) -> authorize
                        .requestMatchers("/api/v1/manage/**").authenticated()
                        .requestMatchers(
                                "/css/**",
                                "/js/**"
                        ).permitAll()
                        .anyRequest().denyAll()
                )
                .csrf(AbstractHttpConfigurer::disable)
                .cors(corsConfigurer -> corsConfigurer
                        .configurationSource(request -> {
                            CorsConfiguration config = new CorsConfiguration();
                            config.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE"));
                            config.setAllowedHeaders(List.of("*"));
                            return config;
                        })
                )
                .oauth2ResourceServer(oauth2ResourceServerConfigurer -> oauth2ResourceServerConfigurer
                        .jwt(Customizer.withDefaults())
                )
                .sessionManagement(sessionManagementConfigurer -> sessionManagementConfigurer
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                );
//                .exceptionHandling(exceptionHandlingConfigurer -> exceptionHandlingConfigurer
//                        .authenticationEntryPoint((request, response, authException) -> {
//                            response.setStatus(HttpStatus.UNAUTHORIZED.value());
//                        })
//                        .accessDeniedHandler((request, response, accessDeniedException) -> {
//                            response.setStatus(HttpStatus.FORBIDDEN.value());
//                        })
//                );
//                .addFilterBefore(oncePerRequestFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

}