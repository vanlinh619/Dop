package org.dop.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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
public class ClientRole {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String name;

    private String description;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    private Oauth2RegisteredClient client;

    /**
     * Relationship
     */
    @OneToMany(mappedBy = UserPrimaryClientRole.Fields.role, fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    private List<UserPrimaryClientRole> userPrimaryClientRoles;
}
