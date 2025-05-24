package org.dop.module.user.pojo.projection;

import java.util.UUID;

public record UserIdAndRoleProjection(
        UUID id,
        String role
) { }
