package org.dop.module.user.service;

import org.dop.module.user.pojo.projection.UserIdAndRoleProjection;

import java.util.List;
import java.util.UUID;

public interface UserRoleService {
    List<UserIdAndRoleProjection> getUserIdAndRole(List<UUID> ids);
}
