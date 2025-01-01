package org.dop.entity.embeded;

import jakarta.persistence.Embeddable;
import lombok.*;

@Embeddable
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AddressEmbedded {
    private String addressLine1;
    private String addressLine2;
    private String street;
    private String city;
    private String state;
    private String postalCode;
    private String country;
}
