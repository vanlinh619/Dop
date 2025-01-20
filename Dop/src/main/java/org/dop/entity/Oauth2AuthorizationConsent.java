package org.dop.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.dop.entity.embeded.ConsentEmbedded;
import org.springframework.security.oauth2.server.authorization.JdbcOAuth2AuthorizationConsentService;

/**
 * See document {@link JdbcOAuth2AuthorizationConsentService} for detail create this entity.
 * <p>
 * <b>IMPORTANT:</b> This {@code OAuth2AuthorizationConsentService} depends on the table
 * definition described in
 * "classpath:org/ springframework/ security/ oauth2/ server/ authorization/ oauth2-authorization-consent-schema.sql"
 * and therefore MUST be defined in the database schema.
 */
@Entity
@Table(name = "oauth2_authorization_consent")
@Getter
@Setter
public class Oauth2AuthorizationConsent {

    @EmbeddedId
    private ConsentEmbedded id;

    @Column(length = 1000)
    private String authorities;
}
