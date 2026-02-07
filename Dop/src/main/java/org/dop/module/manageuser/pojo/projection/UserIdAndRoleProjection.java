package org.dop.module.manageuser.pojo.projection;

import java.util.UUID;

public record UserIdAndRoleProjection(
        UUID id,
        String role
) {
}
