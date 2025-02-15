package org.dop.module.user.service;

import lombok.RequiredArgsConstructor;
import org.dop.module.user.pojo.projection.UserAuthorityProjection;
import org.dop.repository.UserPrimaryRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserPrimaryServiceImpl implements UserPrimaryService {

    private final UserPrimaryRepository userPrimaryRepository;

    @Override
    public Optional<UserAuthorityProjection> findUserAuthority(String identifier) {
        try {
            UUID uuid = UUID.fromString(identifier);
            return userPrimaryRepository.findUserAuthority(uuid);
        } catch (IllegalArgumentException ignore) {
            return userPrimaryRepository.findUserAuthority(identifier);
        }
    }

    @Override
    public List<String> findRoles(UUID userId) {
        return userPrimaryRepository.findRoles(userId);
    }
}
