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
public class DeviceCodeEmbedded {

    @Column(name = "device_code_value", length = Length.LONG32)
    private String value;

    @Column(name = "device_code_issued_at")
    private Instant issuedAt;

    @Column(name = "device_code_expires_at")
    private Instant expiresAt;

    @Column(name = "device_code_metadata", length = Length.LONG32)
    private String metadata;
}
