package org.dop.module.user.mapper;

import org.dop.entity.UserPrimary;
import org.dop.entity.UserProfile;
import org.dop.entity.UserProvider;
import org.dop.entity.embeded.EmailEmbedded;
import org.dop.module.user.pojo.request.UserInfoRequest;
import org.dop.module.user.pojo.response.UserInfoResponse;
import org.mapstruct.*;


@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface UserInfoMapper {

    @Mappings({
            @Mapping(target = "id", source = "userPrimary.id"),
            @Mapping(target = "fullName", source = "userProfile.fullName"),
            @Mapping(target = "email", source = "userPrimary.email"),
            @Mapping(target = "status", source = "userPrimary.status"),
            @Mapping(target = "roles", ignore = true),
            @Mapping(target = "provider", source = "userProvider.provider"),
            @Mapping(target = "createdDate", source = "userPrimary.createdDate"),
            @Mapping(target = "lastModifiedDate", source = "userPrimary.lastModifiedDate")
    })
    UserInfoResponse toUserInfoResponse(UserPrimary userPrimary, UserProfile userProfile, UserProvider userProvider);

    @Mappings({
            @Mapping(target = "roles", ignore = true),
            @Mapping(target = "email", source = "userInfoRequest", qualifiedByName = "toEmail"),
            @Mapping(target = "createdDate", ignore = true),
            @Mapping(target = "id", ignore = true),
            @Mapping(target = "images", ignore = true),
            @Mapping(target = "lastModifiedDate", ignore = true),
            @Mapping(target = "password", ignore = true)
    })
    UserPrimary toUserPrimary(UserInfoRequest userInfoRequest);

    @Mappings({
            @Mapping(target = "address", ignore = true),
            @Mapping(target = "birthDate", ignore = true),
            @Mapping(target = "createdDate", ignore = true),
            @Mapping(target = "fullName", source = "userInfoRequest", qualifiedByName = "toFullName"),
            @Mapping(target = "gender", ignore = true),
            @Mapping(target = "id", ignore = true),
            @Mapping(target = "language", ignore = true),
            @Mapping(target = "lastModifiedDate", ignore = true),
            @Mapping(target = "locale", ignore = true),
            @Mapping(target = "middleName", ignore = true),
            @Mapping(target = "phone", ignore = true),
            @Mapping(target = "story", ignore = true),
            @Mapping(target = "picture", ignore = true),
            @Mapping(target = "profile", ignore = true),
            @Mapping(target = "website", ignore = true),
            @Mapping(target = "zoneInfo", ignore = true)
    })
    UserProfile toUserProfile(UserInfoRequest userInfoRequest);

    @Named("toEmail")
    default EmailEmbedded toEmail(UserInfoRequest userInfoRequest) {
        if (userInfoRequest.getEmail() == null) {
            return null;
        }
        return EmailEmbedded.builder()
                .value(userInfoRequest.getEmail())
                .verified(userInfoRequest.getEmailVerified() != null && userInfoRequest.getEmailVerified())
                .build();
    }

    @Named("toFullName")
    default String toFullName(UserInfoRequest userInfoRequest) {
        return String.format(
                "%s %s",
                userInfoRequest.getFamilyName() == null ? "" : userInfoRequest.getFamilyName(),
                userInfoRequest.getGivenName()
        ).trim();
    }
}
