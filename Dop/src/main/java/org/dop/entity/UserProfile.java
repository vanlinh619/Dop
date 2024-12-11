package org.dop.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.*;
import lombok.experimental.FieldNameConstants;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.Instant;
import java.util.Locale;

@Entity
@Getter
@Setter
@Builder
@FieldNameConstants
@AllArgsConstructor
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class UserProfile {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 50)
    private String familyName;

    @NotNull
    @Column(length = 50)
    private String givenName;

    @NotNull
    @Column(length = 101)
    private String fullName;

    private Instant dateOfBirth;

    @Column(length = 15)
    @Pattern(regexp = "^[0-9+]{10,15}$")
    private String phoneNumber;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    private Language language;

    @OneToOne(fetch = FetchType.LAZY)
    private Image avatar;

    private Locale country;

    private String story;

    @CreatedDate
    private Instant created;

    @LastModifiedDate
    private Instant lastModified;
}
