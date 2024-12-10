package org.dop.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
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
@Getter
@Setter
public class Oauth2RegisteredClient {
    @Id
    @Column(length = 100, nullable = false)
    private String id;

    @Column(length = 100, nullable = false)
    private String clientId;

    @Column(length = 100, nullable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private Instant clientIdIssuedAt;

    @Column(length = 200)
    private String clientSecret;

    @Column(columnDefinition = "TIMESTAMP")
    private Instant clientSecretExpiresAt;

    @Column(length = 200, nullable = false)
    private String clientName;

    @Column(length = 1000, nullable = false)
    private String clientAuthenticationMethods;

    @Column(length = 1000, nullable = false)
    private String authorizationGrantTypes;

    @Column(length = 1000)
    private String redirectUris;

    @Column(length = 1000)
    private String postLogoutRedirectUris;

    @Column(length = 1000, nullable = false)
    private String scopes;

    @Column(length = 2000, nullable = false)
    private String clientSettings;

    @Column(length = 2000, nullable = false)
    private String tokenSettings;
}
