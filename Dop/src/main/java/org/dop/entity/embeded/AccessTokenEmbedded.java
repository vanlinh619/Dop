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
public class AccessTokenEmbedded {

    @Column(name = "access_token_value", length = Length.LONG32)
    private String value;

    @Column(name = "access_token_issued_at")
    private Instant issuedAt;

    @Column(name = "access_token_expires_at")
    private Instant expiresAt;

    @Column(name = "access_token_metadata", length = Length.LONG32)
    private String metadata;

    @Column(name = "access_token_type", length = 100)
    private String type;

    @Column(name = "access_token_scopes", length = 1000)
    private String scopes;
}
