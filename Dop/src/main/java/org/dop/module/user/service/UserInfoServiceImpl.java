package org.dop.module.user.service;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.dop.entity.*;
import org.dop.entity.state.LanguageCode;
import org.dop.entity.state.Provider;
import org.dop.entity.state.UserPrimaryStatus;
import org.dop.module.role.service.RoleService;
import org.dop.module.user.mapper.UserInfoMapper;
import org.dop.module.user.pojo.request.UserInfoRequest;
import org.dop.module.user.pojo.projection.UserConsentProjection;
import org.dop.module.user.pojo.response.UserInfoResponse;
import org.dop.repository.UserPrimaryRepository;
import org.dop.repository.UserProfileRepository;
import org.dop.repository.UserProviderRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserInfoServiceImpl implements UserInfoService {

    private final UserPrimaryRepository userPrimaryRepository;
    private final UserProfileRepository userProfileRepository;
    private final UserProviderRepository userProviderRepository;
    private final RoleService roleService;
    private final UserInfoMapper userInfoMapper;

    private final EntityManager entityManager;


    @Transactional
    @Override
    public UserInfoResponse createUserPrimary(UserInfoRequest userInfoRequest) {
        UserPrimary userPrimary = userInfoMapper.toUserPrimary(userInfoRequest);
        UserProfile userProfile = userInfoMapper.toUserProfile(userInfoRequest);

        /// Create user primary
        userPrimary.setStatus(userInfoRequest.getStatus() == null ? UserPrimaryStatus.DISABLED : userInfoRequest.getStatus());
        /// Find user authority
        if (userInfoRequest.getRoles() != null) {
            List<Role> verifiedRoles = roleService.verifyRole(userInfoRequest.getRoles()).stream()
                    .map(roleId -> entityManager.getReference(Role.class, roleId))
                    .toList();
            userPrimary.setRoles(verifiedRoles);
        }
        userPrimaryRepository.save(userPrimary);

        /// Create user profile
        userProfile.setId(userPrimary.getId());
        if (userInfoRequest.getLanguageCode() != null) {
            userProfile.setLanguage(entityManager.getReference(Language.class, userInfoRequest.getLanguageCode()));
        } else {
            userProfile.setLanguage(entityManager.getReference(Language.class, LanguageCode.vi));
        }
        userProfileRepository.save(userProfile);

        /// Create user provider
        UserProvider userProvider = UserProvider.builder()
                .id(userPrimary.getId())
                .providerId(userPrimary.getId().toString())
                .provider(Provider.LOCAL)
                .build();
        userProviderRepository.save(userProvider);

        return userInfoMapper.toUserInfoResponse(userPrimary, userProfile, userProvider);
    }

    @Override
    public UserConsentProjection getUserConsentInfo(String uuid) {
        UUID id = UUID.fromString(uuid);
        return userProfileRepository.getUserConsentInfo(id);
    }
}
