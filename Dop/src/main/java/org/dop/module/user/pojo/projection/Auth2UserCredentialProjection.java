package org.dop.module.user.pojo.projection;

import org.dop.entity.state.UserPrimaryStatus;

import java.util.UUID;

public record Auth2UserCredentialProjection(
        UUID id,
        UserPrimaryStatus status
) {}
