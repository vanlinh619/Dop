package org.dop.entity.embeded;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.Email;
import lombok.*;

@Embeddable
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EmailEmbedded {

    @Email
    @Column(name = "email", unique = true)
    private String value;

    @Column(name = "email_verified")
    private Boolean verified;
}
