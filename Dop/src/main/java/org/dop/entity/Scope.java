package org.dop.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import lombok.experimental.FieldNameConstants;

@Entity
@Getter
@Setter
@Builder
@FieldNameConstants
@AllArgsConstructor
@NoArgsConstructor
public class Scope {

    @Id
    private String id;

    @NotBlank
    private String name;

    /// Null indicates that the scope is associated with all clients
    @ManyToOne(fetch = FetchType.LAZY)
    private Oauth2RegisteredClient client;

    /// As a default description when locale is not provided or not found
    private String description;
}
