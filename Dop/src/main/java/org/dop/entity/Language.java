package org.dop.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import org.dop.entity.state.LanguageCode;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class Language {

    /**
     * Example vi, en
     * */
    @Id
    @Enumerated(EnumType.STRING)
    private LanguageCode id;

    @NotBlank
    private String displayName;
}
