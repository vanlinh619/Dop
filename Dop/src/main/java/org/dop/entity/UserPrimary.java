package org.dop.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.*;
import lombok.experimental.FieldNameConstants;
import org.dop.entity.embeded.EmailEmbedded;
import org.dop.entity.state.UserPrimaryRole;
import org.dop.entity.state.UserPrimaryStatus;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.Instant;
import java.util.UUID;

@Entity
@Getter
@Setter
@Builder
@FieldNameConstants
@AllArgsConstructor
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class UserPrimary {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @NotBlank
    @Pattern(regexp = "^[a-zA-Z0-9-]{4,50}$")
    @Column(unique = true, length = 50)
    private String username;

    @NotBlank
    private String password;

    @NotNull
    @Embedded
    private EmailEmbedded email;

    @NotNull
    @Enumerated(EnumType.STRING)
    private UserPrimaryRole role;

    @NotNull
    @Enumerated(EnumType.STRING)
    private UserPrimaryStatus status;

    @CreatedDate
    private Instant createdDate;

    @LastModifiedDate
    private Instant lastModifiedDate;
}
