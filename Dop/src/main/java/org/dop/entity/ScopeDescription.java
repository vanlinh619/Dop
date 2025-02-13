package org.dop.entity;

import jakarta.persistence.*;
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
public class ScopeDescription {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private Scope scope;

    @ManyToOne(fetch = FetchType.LAZY)
    private Language language;

    @NotBlank
    @Column(length = 1000)
    private String description;
}
