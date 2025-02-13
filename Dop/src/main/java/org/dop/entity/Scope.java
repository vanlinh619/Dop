package org.dop.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
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

    /// as default description when locale is not provided or not found
    private String description;
}
