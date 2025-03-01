package org.dop.module.user.pojo.data;

import lombok.*;

import java.util.Set;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserJitData {
    private UUID id;
    Set<String> roles;
}
