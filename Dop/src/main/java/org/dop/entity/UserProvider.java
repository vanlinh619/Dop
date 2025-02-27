package org.dop.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.FieldNameConstants;
import org.dop.entity.state.Provider;
import org.hibernate.Length;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.util.Map;
import java.util.UUID;

@Entity
@Getter
@Setter
@Builder
@FieldNameConstants
@AllArgsConstructor
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
@Table(uniqueConstraints = {
        @UniqueConstraint(columnNames = {"provider", "providerId"})
})
public class UserProvider {
    /**
     * This is User primary id
     */
    @Id
    private UUID id;

    @NotNull
    @Enumerated(EnumType.STRING)
    private Provider provider;

    @NotBlank
    private String providerId;

    @Column(length = Length.LONG32)
    private String claims;
}
