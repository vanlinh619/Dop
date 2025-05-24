package org.dop.module.user.service;

import lombok.RequiredArgsConstructor;
import org.dop.module.user.pojo.projection.UserIdAndRoleProjection;
import org.dop.repository.UserPrimaryRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserRoleServiceImpl implements UserRoleService {

    private final UserPrimaryRepository userPrimaryRepository;

    @Override
    public List<UserIdAndRoleProjection> getUserIdAndRole(List<UUID> ids) {
        return userPrimaryRepository.findAllIdAndRole(ids);
    }
}
