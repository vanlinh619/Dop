package org.dop.config;

import java.util.List;
import java.util.Objects;
import java.util.function.Supplier;
import java.util.stream.Stream;

import org.dop.config.property.Oauth2AuthorizationServerProperties;
import org.dop.config.property.Oauth2LoginProperties;
import org.dop.config.property.SecurityRememberMeProperties;
import org.dop.entity.state.Provider;
import org.dop.module.security.oauth2login.service.DopOidcUserService;
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
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.InMemoryClientRegistrationRepository;
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
                        /// Add custom consent page
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
            SecurityRememberMeProperties securityRememberMeProperties,
            Oauth2AuthorizationServerProperties oauth2AuthorizationServerProperties,
            Oauth2LoginProperties oauth2LoginProperties,
            List<Supplier<ClientRegistration>> clientRegistrationSuppliers,
            Supplier<DopOidcUserService> dopOidcUserServiceSupplier
    ) throws Exception {
        http
                .securityMatcher(Stream.of(
                        oauth2AuthorizationServerProperties.getConsentPageEndpoint(),
                        "/login/**",
                        oauth2LoginProperties.isSocialEnable(Provider.GOOGLE)
                                ? oauth2LoginProperties.getGoogleAuthorizationEndpoint()
                                : null
                ).filter(Objects::nonNull).toArray(String[]::new))
                .authorizeHttpRequests((authorize) -> authorize
                        .requestMatchers(oauth2AuthorizationServerProperties.getConsentPageEndpoint()).authenticated()
                        .requestMatchers("/login/**").permitAll()
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

        if (oauth2LoginProperties.anySocialEnable()) {
            List<ClientRegistration> clientRegistrations = clientRegistrationSuppliers.stream()
                    .map(Supplier::get)
                    .filter(Objects::nonNull)
                    .toList();

            http.oauth2Login(oauth2LoginConfigurer -> oauth2LoginConfigurer
                    .clientRegistrationRepository(new InMemoryClientRegistrationRepository(clientRegistrations))
                    .loginPage("/login")
                    .userInfoEndpoint(userInfoEndpointConfig -> userInfoEndpointConfig
                            .oidcUserService(dopOidcUserServiceSupplier.get())
                    )
            );
        }
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