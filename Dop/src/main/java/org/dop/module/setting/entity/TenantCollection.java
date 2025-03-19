package org.dop.module.setting.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.*;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TenantCollection {

    /// Schema name as an id
    @Id
    private String id;
}
