package org.dop.module.user.service;

import lombok.RequiredArgsConstructor;
import org.dop.module.user.pojo.projection.UserAuthorityProjection;
import org.dop.repository.UserPrimaryRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserPrimaryServiceImpl implements UserPrimaryService {

    private final UserPrimaryRepository userPrimaryRepository;

    @Override
    public Optional<UserAuthorityProjection> findUserAuthority(String identifier) {
        return userPrimaryRepository.findUserAuthority(identifier);
    }
}
