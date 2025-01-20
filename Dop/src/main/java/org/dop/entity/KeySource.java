package org.dop.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.dop.entity.state.AlgorithmKey;
import org.hibernate.Length;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class KeySource {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Enumerated(EnumType.STRING)
    private AlgorithmKey algorithm;

    @NotNull
    @Column(length = Length.LONG32)
    private String source;
}
