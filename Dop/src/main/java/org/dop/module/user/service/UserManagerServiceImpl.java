package org.dop.module.user.service;

import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.dop.entity.*;
import org.dop.entity.state.LanguageCode;
import org.dop.entity.state.Provider;
import org.dop.entity.state.UserPrimaryStatus;
import org.dop.module.common.pojo.response.PageResponse;
import org.dop.module.role.service.RoleService;
import org.dop.module.user.mapper.UserInfoMapper;
import org.dop.module.user.pojo.projection.UserIdAndRoleProjection;
import org.dop.module.user.pojo.request.UserInfoRequest;
import org.dop.module.user.pojo.request.UserPageRequest;
import org.dop.module.user.pojo.response.UserInfoResponse;
import org.dop.repository.UserPrimaryDslRepository;
import org.dop.repository.UserPrimaryRepository;
import org.dop.repository.UserProfileRepository;
import org.dop.repository.UserProviderRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserManagerServiceImpl implements UserManagerService {

    private final UserPrimaryRepository userPrimaryRepository;
    private final UserPrimaryDslRepository userPrimaryDslRepository;
    private final UserProfileRepository userProfileRepository;
    private final UserProviderRepository userProviderRepository;
    private final RoleService roleService;
    private final UserRoleService userRoleService;
    private final UserInfoMapper userInfoMapper;

    private final EntityManager entityManager;


    @Transactional
    @Override
    public UserInfoResponse createUserPrimary(UserInfoRequest userInfoRequest) {
        UserPrimary userPrimary = userInfoMapper.toUserPrimary(userInfoRequest);
        UserProfile userProfile = userInfoMapper.toUserProfile(userInfoRequest);

        // Create user primary
        userPrimary.setStatus(userInfoRequest.getStatus() == null ? UserPrimaryStatus.DISABLED : userInfoRequest.getStatus());
        // Find user authority
        if (userInfoRequest.getRoles() != null) {
            Set<Role> verifiedRoles = roleService.verifyRole(userInfoRequest.getRoles()).stream()
                    .map(roleId -> entityManager.getReference(Role.class, roleId))
                    .collect(Collectors.toSet());
            userPrimary.setRoles(verifiedRoles);
        }
        userPrimaryRepository.save(userPrimary);

        // Create user profile
        userProfile.setId(userPrimary.getId());
        if (userInfoRequest.getLanguageCode() != null) {
            userProfile.setLanguage(entityManager.getReference(Language.class, userInfoRequest.getLanguageCode()));
        } else {
            userProfile.setLanguage(entityManager.getReference(Language.class, LanguageCode.vi));
        }
        userProfileRepository.save(userProfile);

        // Create user provider
        UserProvider userProvider = UserProvider.builder()
                .id(userPrimary.getId())
                .providerId(userPrimary.getId().toString())
                .provider(Provider.LOCAL)
                .build();
        userProviderRepository.save(userProvider);

        return userInfoMapper.toUserInfoResponse(userPrimary, userProfile);
    }

    @Override
    public PageResponse<UserInfoResponse> listAllUserPage(UserPageRequest userPageRequest) {

        int page = userPageRequest.getPage() - 1;
        Pageable pageable = PageRequest.of(
                page,
                userPageRequest.getSize(),
                Sort.by(userPageRequest.getDirection(), userPageRequest.getSortName())
        );

        List<UserInfoResponse> userInfoResponses = userPrimaryDslRepository.listUserPage(userPageRequest.getSearch(), pageable);
        List<UUID> userIds = userInfoResponses.stream()
                .map(UserInfoResponse::getId)
                .toList();
        Map<UUID, List<String>> map = userRoleService.getUserIdAndRole(userIds).stream()
                .collect(Collectors.groupingBy(
                        UserIdAndRoleProjection::id,
                        Collectors.mapping(UserIdAndRoleProjection::role, Collectors.toList())
                ));

        userInfoResponses.forEach(userInfoResponse -> {
            List<String> roles = map.getOrDefault(userInfoResponse.getId(), List.of());
            userInfoResponse.setRoles(roles);
        });

        long total = userPrimaryDslRepository.countTotal(userPageRequest.getSearch());

        return PageResponse.<UserInfoResponse>builder()
                .content(userInfoResponses)
                .page(userPageRequest.getPage())
                .size(userPageRequest.getSize())
                .total(total)
                .build();
    }
}
