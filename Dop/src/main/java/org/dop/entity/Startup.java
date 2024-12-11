package org.dop.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldNameConstants;
import org.dop.entity.state.StartupName;

@Entity
@Table(indexes = @Index(name = "index_startup_name", columnList = Startup.Fields.name))
@Getter
@Setter
@Builder
@FieldNameConstants
@AllArgsConstructor
@NoArgsConstructor
public class Startup {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, unique = true)

    private StartupName name;
}
