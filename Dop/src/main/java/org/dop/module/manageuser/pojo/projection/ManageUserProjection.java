package org.dop.module.manageuser.pojo.projection;

import org.dop.entity.state.UserPrimaryStatus;

import java.time.Instant;
import java.util.UUID;

public record ManageUserProjection(
        UUID id,
        String fullName,
        String email,
        Boolean emailVerified,
        UserPrimaryStatus status,
        Instant createdDate,
        Instant lastModifiedDate
) {
}
