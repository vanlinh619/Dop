package org.dop.module.user.pojo.projection;

import org.dop.entity.state.UserPrimaryStatus;

import java.util.UUID;

public record UserCredentialProjection(
        UUID id,
        String password,
        UserPrimaryStatus status
) {}
