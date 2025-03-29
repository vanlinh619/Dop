package org.dop.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.FieldNameConstants;
import org.dop.entity.embeded.AddressEmbedded;
import org.dop.entity.embeded.PhoneEmbedded;
import org.dop.entity.state.Gender;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.Instant;
import java.time.ZoneId;
import java.util.Locale;
import java.util.UUID;

@Entity
@Getter
@Setter
@Builder
@FieldNameConstants
@AllArgsConstructor
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class UserProfile {
    /**
     * This is User primary id
     */
    @Id
    private UUID id;

    @Column(length = 50)
    private String familyName;

    @Column(length = 50)
    private String middleName;

    @NotNull
    @Column(length = 50)
    private String givenName;

    @NotNull
    private String fullName;

    private Instant birthDate;

    private Gender gender;

    private PhoneEmbedded phone;

    private AddressEmbedded address;

    private String profile;

    private String website;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    private Language language;

    private String picture;

    private Locale locale;

    private ZoneId zoneInfo;

    private String story;

    @CreatedDate
    private Instant createdDate;

    @LastModifiedDate
    private Instant lastModifiedDate;
}
