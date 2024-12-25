package org.dop.module.user.service;

import org.dop.module.user.pojo.projection.UserAuthorityProjection;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserPrimaryService {

    Optional<UserAuthorityProjection> findUserAuthority(String identifier);

    List<String> findRoles(UUID userId);
}
