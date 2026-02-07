package org.dop.module.manageuser.service;

import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.dop.entity.*;
import org.dop.entity.embeded.EmailEmbedded;
import org.dop.entity.state.LanguageCode;
import org.dop.entity.state.Provider;
import org.dop.entity.state.UserPrimaryStatus;
import org.dop.module.common.pojo.response.PageResponse;
import org.dop.module.manageuser.mapper.UserInfoMapper;
import org.dop.module.manageuser.pojo.projection.ManageUserProjection;
import org.dop.module.manageuser.pojo.projection.UserIdAndRoleProjection;
import org.dop.module.manageuser.pojo.request.UserInfoRequest;
import org.dop.module.manageuser.pojo.request.UserPageRequest;
import org.dop.module.manageuser.pojo.response.UserInfoResponse;
import org.dop.module.manageuser.repository.ManageUserRepository;
import org.dop.module.role.service.RoleService;
import org.dop.repository.UserPrimaryRepository;
import org.dop.repository.UserProfileRepository;
import org.dop.repository.UserProviderRepository;
import org.springframework.data.domain.Page;
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
    private final ManageUserRepository manageUserRepository;
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

        Page<ManageUserProjection> manageUserProjections = manageUserRepository.getUserPage(userPageRequest.getSearch(), pageable);

        List<UUID> userIds = manageUserProjections.stream()
                .map(ManageUserProjection::id)
                .toList();
        Map<UUID, List<String>> map = userRoleService.getUserIdAndRole(userIds).stream()
                .collect(Collectors.groupingBy(
                        UserIdAndRoleProjection::id,
                        Collectors.mapping(UserIdAndRoleProjection::role, Collectors.toList())
                ));

        List<UserInfoResponse> userInfoResponses = manageUserProjections.stream()
                .map(manageUserProjection -> {
                    List<String> roles = map.getOrDefault(manageUserProjection.id(), List.of());
                    return UserInfoResponse.builder()
                            .id(manageUserProjection.id())
                            .fullName(manageUserProjection.fullName())
                            .email(EmailEmbedded.builder()
                                    .value(manageUserProjection.email())
                                    .verified(manageUserProjection.emailVerified())
                                    .build())
                            .status(manageUserProjection.status())
                            .roles(roles)
                            .createdDate(manageUserProjection.createdDate())
                            .lastModifiedDate(manageUserProjection.lastModifiedDate())
                            .build();
                })
                .toList();

        long total = manageUserProjections.getTotalElements();

        return PageResponse.<UserInfoResponse>builder()
                .content(userInfoResponses)
                .page(userPageRequest.getPage())
                .size(userPageRequest.getSize())
                .total(total)
                .build();
    }
}
