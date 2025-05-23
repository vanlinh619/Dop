package org.dop.entity.embeded;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.Pattern;
import lombok.*;

@Embeddable
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PhoneEmbedded {

    @Column(name = "phone_number", length = 15)
    @Pattern(regexp = "^[0-9+]{10,15}$")
    private String number;

    @Column(name = "phone_verified")
    private Boolean verified;
}
