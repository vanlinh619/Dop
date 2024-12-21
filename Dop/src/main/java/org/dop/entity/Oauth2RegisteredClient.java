package org.dop.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.oauth2.server.authorization.client.JdbcRegisteredClientRepository;

import java.time.Instant;

/**
 * See document {@link JdbcRegisteredClientRepository} for detail create this entity.
 * <p>
 * <b>IMPORTANT:</b> This {@code RegisteredClientRepository} depends on the table
 * definition described in
 * "classpath:org/springframework/security/oauth2/server/authorization/client/oauth2-registered-client-schema.sql"
 * and therefore MUST be defined in the database schema.
 */
@Entity
@Table(name = "oauth2_registered_client")
@Getter
@Setter
public class Oauth2RegisteredClient {
    @Id
    @Column(length = 100)
    private String id;

    @NotBlank
    @Column(length = 100)
    private String clientId;

    @NotNull
    @Column(length = 100)
    private Instant clientIdIssuedAt;

    @Column(length = 200)
    private String clientSecret;

    private Instant clientSecretExpiresAt;

    @NotBlank
    @Column(length = 200)
    private String clientName;

    @NotBlank
    @Column(length = 1000)
    private String clientAuthenticationMethods;

    @NotBlank
    @Column(length = 1000)
    private String authorizationGrantTypes;

    @Column(length = 1000)
    private String redirectUris;

    @Column(length = 1000)
    private String postLogoutRedirectUris;

    @NotBlank
    @Column(length = 1000)
    private String scopes;

    @NotBlank
    @Column(length = 2000)
    private String clientSettings;

    @NotBlank
    @Column(length = 2000)
    private String tokenSettings;
}
