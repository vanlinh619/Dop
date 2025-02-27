package org.dop.module.user.service;

import com.nimbusds.jose.shaded.gson.Gson;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.dop.config.property.RoleDefaultProperties;
import org.dop.entity.*;
import org.dop.entity.embeded.AddressEmbedded;
import org.dop.entity.embeded.EmailEmbedded;
import org.dop.entity.embeded.PhoneEmbedded;
import org.dop.entity.state.Gender;
import org.dop.entity.state.LanguageCode;
import org.dop.entity.state.Provider;
import org.dop.entity.state.UserPrimaryStatus;
import org.dop.module.helper.DateTimeHelper;
import org.dop.module.language.service.LanguageService;
import org.dop.module.role.service.RoleService;
import org.dop.module.user.mapper.UserInfoMapper;
import org.dop.module.user.pojo.data.UserJitData;
import org.dop.module.user.pojo.projection.Auth2UserCredentialProjection;
import org.dop.module.user.pojo.projection.UserCredentialProjection;
import org.dop.module.user.pojo.request.UserInfoRequest;
import org.dop.module.user.pojo.projection.UserConsentProjection;
import org.dop.module.user.pojo.response.UserInfoResponse;
import org.dop.repository.UserPrimaryRepository;
import org.dop.repository.UserProfileRepository;
import org.dop.repository.UserProviderRepository;
import org.springframework.security.oauth2.core.oidc.AddressStandardClaim;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.*;

@Service
@RequiredArgsConstructor
public class UserInfoServiceImpl implements UserInfoService {

    private final UserPrimaryRepository userPrimaryRepository;
    private final UserProfileRepository userProfileRepository;
    private final UserProviderRepository userProviderRepository;
    private final RoleService roleService;
    private final UserInfoMapper userInfoMapper;
    private final DateTimeHelper dateTimeHelper;
    private final LanguageService  languageService;
    private final Gson gson;

    private final RoleDefaultProperties roleDefaultProperties;
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

    @Transactional
    @Override
    public UserJitData persistUserOidc(OidcUser oidcUser, String registrationId) {
        Map<String, Object> claims = oidcUser.getClaims();

        /// User primary
        UserPrimary userPrimary = UserPrimary.builder()
                .username(UUID.randomUUID().toString())
                .status(UserPrimaryStatus.ENABLED)
                .roles(List.of(entityManager.getReference(Role.class, roleDefaultProperties.getRoleUser())))
                .build();
        String email = oidcUser.getEmail();
        if (StringUtils.hasText(email)) {
            userPrimary.setEmail(EmailEmbedded.builder()
                    .value(email)
                    .verified(oidcUser.getEmailVerified())
                    .build());
        }
        userPrimaryRepository.save(userPrimary);

        /// User profile
        UserProfile userProfile = UserProfile.builder()
                .id(userPrimary.getId())
                .familyName(oidcUser.getFamilyName())
                .middleName(oidcUser.getMiddleName())
                .givenName(oidcUser.getGivenName())
                .fullName(oidcUser.getFullName())
                .birthDate(dateTimeHelper.parse(oidcUser.getBirthdate()))
                .gender(Gender.parse(oidcUser.getGender()))
                .language(entityManager.getReference(Language.class, languageService.getLanguageCodeDefault(oidcUser.getLocale())))
                .build();
        String phoneNumber = oidcUser.getPhoneNumber();
        if (StringUtils.hasText(phoneNumber)) {
            userProfile.setPhone(PhoneEmbedded.builder()
                    .number(phoneNumber)
                    .verified(oidcUser.getPhoneNumberVerified())
                    .build());
        }
        AddressStandardClaim addressStandardClaim = oidcUser.getAddress();
        if (addressStandardClaim != null) {
            AddressEmbedded addressEmbedded = AddressEmbedded.builder()
                    .street(addressStandardClaim.getStreetAddress())
                    .city(addressStandardClaim.getLocality())
                    .state(addressStandardClaim.getRegion())
                    .postalCode(addressStandardClaim.getPostalCode())
                    .country(addressStandardClaim.getCountry())
                    .addressLine1(addressStandardClaim.getFormatted())
                    .build();
            userProfile.setAddress(addressEmbedded);
        }
        String locale = oidcUser.getLocale();
        if (StringUtils.hasText(locale)) {
            userProfile.setLocale(Locale.of(locale));
        }
        userProfileRepository.save(userProfile);

        /// User provider
        UserProvider userProvider = UserProvider.builder()
                .id(userPrimary.getId())
                .provider(Provider.parse(registrationId))
                .providerId(oidcUser.getSubject())
                .claims(gson.toJson(claims))
                .build();
        userProviderRepository.save(userProvider);

        /// UserJitData
        return UserJitData.builder()
                .id(userPrimary.getId())
                .roles(userPrimary.getRoles().stream()
                        .map(Role::getId)
                        .toList())
                .build();
    }

    @Override
    public UserConsentProjection getUserConsentInfo(String uuid) {
        UUID id = UUID.fromString(uuid);
        return userProfileRepository.getUserConsentInfo(id);
    }

    @Override
    public Optional<UserCredentialProjection> findUserCredential(String identifier) {
        try {
            UUID uuid = UUID.fromString(identifier);
            return userPrimaryRepository.findUserAuthority(uuid);
        } catch (IllegalArgumentException ignore) {
            return userPrimaryRepository.findUserAuthority(identifier);
        }
    }

    @Override
    public Set<String> findRoles(UUID userId) {
        return userPrimaryRepository.findRoles(userId);
    }

    @Override
    public Optional<Auth2UserCredentialProjection> findAuth2UserCredential(String subject) {
        return userPrimaryRepository.findAuth2User(subject);
    }
}
