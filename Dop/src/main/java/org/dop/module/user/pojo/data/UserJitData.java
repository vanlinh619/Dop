package org.dop.module.user.pojo.data;

import lombok.*;

import java.util.List;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserJitData {
    private UUID id;
    List<String> roles;
}
