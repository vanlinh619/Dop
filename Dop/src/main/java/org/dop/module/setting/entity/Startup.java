package org.dop.module.setting.entity;

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
@Table(
        indexes = @Index(name = "index_startup_name", columnList = Startup.Fields.name),
        uniqueConstraints = @UniqueConstraint(name = "unique_startup_name_schema", columnNames = {Startup.Fields.name, Startup.Fields.schema})
)
public class Startup {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String name;

    @NotBlank
    private String schema;

    private Integer priority;
}
