package org.dop.entity.embeded;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Embeddable
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EmailEmbedded {

    @Email
    @NotBlank
    @Column(name = "email", unique = true)
    private String value;

    @NotNull
    @Column(name = "email_verified")
    private Boolean verified;
}
