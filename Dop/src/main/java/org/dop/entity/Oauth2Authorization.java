package org.dop.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import org.dop.entity.embeded.*;
import org.hibernate.Length;
import org.springframework.security.oauth2.server.authorization.JdbcOAuth2AuthorizationService;


/**
 * See document {@link JdbcOAuth2AuthorizationService} for detail create this entity.
 * <p>
 * <b>IMPORTANT:</b> This {@code OAuth2AuthorizationService} depends on the table
 * definition described in
 * "classpath:org/springframework/security/oauth2/server/authorization/oauth2-authorization-schema.sql"
 * and therefore MUST be defined in the database schema.
 */
@Entity
@Table(name = "oauth2_authorization")
@Getter
@Setter
public class Oauth2Authorization {

    @Id
    @Column(length = 100)
    private String id;

    @NotBlank
    @Column(length = 100)
    private String registeredClientId;

    @NotBlank
    @Column(length = 200)
    private String principalName;

    @NotBlank
    @Column(length = 100)
    private String authorizationGrantType;

    @Column(length = 1000)
    private String authorizedScopes;

    @Column(length = Length.LONG32)
    private String attributes;

    @Column(length = 500)
    private String state;

    @Embedded
    private AuthorizationCodeEmbedded authorizationCode;

    @Embedded
    private AccessTokenEmbedded accessToken;

    @Embedded
    private OidcIdTokenEmbedded oidcIdToken;

    @Embedded
    private RefreshTokenEmbedded refreshToken;

    @Embedded
    private UserCodeEmbedded userCode;

    @Embedded
    private DeviceCodeEmbedded deviceCode;
}
