package org.dop.module.manageuser.pojo.projection;

import org.dop.entity.state.UserPrimaryStatus;

import java.util.UUID;

public record Auth2UserAuthenticatedProjection(
        UUID id,
        UserPrimaryStatus status
) {
}
