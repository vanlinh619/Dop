package org.dop.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import lombok.experimental.FieldNameConstants;

import java.util.List;

@Entity
@Getter
@Setter
@Builder
@FieldNameConstants
@AllArgsConstructor
@NoArgsConstructor
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String name;

    private String description;

    /**
     * Relationship
     */
    @OneToMany(mappedBy = UserPrimaryRole.Fields.role, fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    private List<UserPrimaryRole> userPrimaryRoles;
}
