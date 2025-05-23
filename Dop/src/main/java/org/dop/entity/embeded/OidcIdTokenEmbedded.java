package org.dop.entity.embeded;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.*;
import org.hibernate.Length;

import java.time.Instant;

@Embeddable
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OidcIdTokenEmbedded {

    @Column(name = "oidc_id_token_value", length = Length.LONG32)
    private String value;

    @Column(name = "oidc_id_token_issued_at")
    private Instant issuedAt;

    @Column(name = "oidc_id_token_expires_at")
    private Instant expiresAt;

    @Column(name = "oidc_id_token_metadata", length = Length.LONG32)
    private String metadata;
}
