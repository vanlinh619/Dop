package org.dop.entity.embeded;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.*;

@Embeddable
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ConsentEmbedded {
    @Column(length = 100)
    private String registeredClientId;

    @Column(length = 200)
    private String principalName;
}
