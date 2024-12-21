package org.dop.module.user.service;

import org.dop.module.user.pojo.projection.UserAuthorityProjection;

import java.util.Optional;

public interface UserPrimaryService {

    Optional<UserAuthorityProjection> findUserAuthority(String identifier);
}
