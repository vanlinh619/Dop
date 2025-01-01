package org.dop.config;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.util.List;
import java.util.UUID;

import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.source.ImmutableJWKSet;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.proc.SecurityContext;
import org.dop.config.property.SecurityRememberMeProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.server.authorization.JdbcOAuth2AuthorizationService;
import org.springframework.security.oauth2.server.authorization.OAuth2AuthorizationService;
import org.springframework.security.oauth2.server.authorization.client.JdbcRegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.config.annotation.web.configuration.OAuth2AuthorizationServerConfiguration;
import org.springframework.security.oauth2.server.authorization.config.annotation.web.configurers.OAuth2AuthorizationServerConfigurer;
import org.springframework.security.oauth2.server.authorization.settings.AuthorizationServerSettings;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;
import org.springframework.security.web.util.matcher.MediaTypeRequestMatcher;
import org.springframework.web.cors.CorsConfiguration;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    public static final String ROLE_PREFIX = "ROLE_";

    /**
     * Config authentication server
     */
    @Bean
    @Order(1)
    public SecurityFilterChain authorizationServerSecurityFilterChainCustom(HttpSecurity http) throws Exception {
        OAuth2AuthorizationServerConfigurer authorizationServerConfigurer = OAuth2AuthorizationServerConfigurer.authorizationServer();

        http
                .securityMatcher(authorizationServerConfigurer.getEndpointsMatcher())
                .authorizeHttpRequests(authorize -> authorize
                        .anyRequest().authenticated()
                )
                .with(authorizationServerConfigurer, (authorizationServer) -> authorizationServer
                        /// Enable OpenID Connect 1.0
                        .oidc(Customizer.withDefaults())
                )
                // Redirect to the login page when not authenticated from the authorization endpoint
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
                        .anyRequest().authenticated()
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
                        .requestMatchers("/api/v1/**").authenticated()
                        .requestMatchers(
                                "/css/**",
                                "/js/**"
                        ).permitAll()
                        .anyRequest().authenticated()
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

    /**
     * Register an {@link RegisteredClientRepository} with a default {@link RegisteredClient} to test
     *
     * @return {@link RegisteredClientRepository}
     */
    @Bean
    public RegisteredClientRepository registeredClientRepository(JdbcTemplate jdbcTemplate) {
        return new JdbcRegisteredClientRepository(jdbcTemplate);
    }

    @Bean
    public OAuth2AuthorizationService authorizationService(JdbcTemplate jdbcTemplate, RegisteredClientRepository registeredClientRepository) {
        return new JdbcOAuth2AuthorizationService(jdbcTemplate, registeredClientRepository);
    }

    @Bean
    public JwtDecoder jwtDecoder(JWKSource<SecurityContext> jwkSource) {
        return OAuth2AuthorizationServerConfiguration.jwtDecoder(jwkSource);
    }

    @Bean
    public JWKSource<SecurityContext> jwkSource() {
        KeyPair keyPair = generateRsaKey();
        RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();
        RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();
        RSAKey rsaKey = new RSAKey.Builder(publicKey)
                .privateKey(privateKey)
                .keyID(UUID.randomUUID().toString())
                .build();
        JWKSet jwkSet = new JWKSet(rsaKey);
        return new ImmutableJWKSet<>(jwkSet);
    }

    private static KeyPair generateRsaKey() {
        KeyPair keyPair;
        try {
            KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
            keyPairGenerator.initialize(2048);
            keyPair = keyPairGenerator.generateKeyPair();
        } catch (Exception ex) {
            throw new IllegalStateException(ex);
        }
        return keyPair;
    }

    @Bean
    public AuthorizationServerSettings authorizationServerSettings() {
        return AuthorizationServerSettings.builder()
                .authorizationEndpoint("/oauth2/authorize")
                .deviceAuthorizationEndpoint("/oauth2/device_authorization")
                .deviceVerificationEndpoint("/oauth2/device_verification")
                .tokenEndpoint("/oauth2/token")
                .tokenIntrospectionEndpoint("/oauth2/introspect")
                .tokenRevocationEndpoint("/oauth2/revoke")
                .jwkSetEndpoint("/oauth2/jwks")
                .oidcLogoutEndpoint("/connect/logout")
                .oidcUserInfoEndpoint("/connect/userinfo")
                .oidcClientRegistrationEndpoint("/connect/register")
                .build();
    }

}