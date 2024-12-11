package org.dop.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.FieldNameConstants;
import org.dop.entity.state.ImageExtension;
import org.hibernate.validator.constraints.Range;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.Instant;
import java.util.UUID;

@Entity
@Table(uniqueConstraints = @UniqueConstraint(columnNames = {Image.Fields.folder, Image.Fields.hash}) )
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldNameConstants
@EntityListeners(AuditingEntityListener.class)
public class Image {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @NotBlank
    private String hash;

    /**
     * Use username as folder. See {@link UserPrimary}
     * */
    @NotBlank
    @Column(length = 50)
    private String folder;

    @NotBlank
    private String name;

    @Range(min = 1, max = 5000000)
    @NotNull
    private Long size;

    @NotNull
    @Enumerated(EnumType.STRING)
    private ImageExtension extension;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    private UserPrimary owner;

    @CreatedDate
    private Instant createdDate;

    @LastModifiedDate
    private Instant lastModifiedDate;
}
