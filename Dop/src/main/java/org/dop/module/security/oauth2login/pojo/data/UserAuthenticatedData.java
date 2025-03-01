package org.dop.module.security.oauth2login.pojo.data;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;
import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserAuthenticatedData {
    private UUID id;
    private Set<String> roles;
}
