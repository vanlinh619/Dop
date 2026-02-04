package org.dop.module.manageuser.pojo.projection;

import org.dop.entity.state.UserPrimaryStatus;

import java.util.UUID;

public record UserAuthenticatedProjection(
        UUID id,
        String password,
        UserPrimaryStatus status
) {
}
