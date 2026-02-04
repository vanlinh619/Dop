package org.dop.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.FieldNameConstants;
import org.dop.entity.state.IdentifierMethod;

import java.util.UUID;

@Entity
@Getter
@Setter
@Builder
@FieldNameConstants
@AllArgsConstructor
@NoArgsConstructor
@Table(uniqueConstraints = {
        @UniqueConstraint(columnNames = {"userId", "identifierMethod"})
})
public class UserIdentifierMethod {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    private UserPrimary user;

    @NotNull
    @Enumerated(EnumType.STRING)
    private IdentifierMethod identifierMethod;
}
