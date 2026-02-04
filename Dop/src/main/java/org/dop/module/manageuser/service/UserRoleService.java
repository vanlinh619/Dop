package org.dop.module.manageuser.service;

import org.dop.module.manageuser.pojo.projection.UserIdAndRoleProjection;

import java.util.List;
import java.util.UUID;

public interface UserRoleService {
    List<UserIdAndRoleProjection> getUserIdAndRole(List<UUID> ids);
}
