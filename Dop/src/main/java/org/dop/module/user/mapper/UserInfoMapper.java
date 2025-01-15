package org.dop.module.user.mapper;

import org.dop.entity.Role;
import org.dop.entity.UserPrimary;
import org.dop.entity.UserProfile;
import org.dop.module.user.pojo.request.UserInfoRequest;
import org.dop.module.user.pojo.response.UserInfoResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.Named;


@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface UserInfoMapper {

    @Mapping(target = UserInfoResponse.Fields.roles, ignore = true)
    UserInfoResponse toUserInfoResponse(UserPrimary userPrimary);

    @Mapping(target = UserPrimary.Fields.roles, source = UserInfoRequest.Fields.roles, qualifiedByName = "toRole")
    UserPrimary toUserPrimary(UserInfoRequest userInfoRequest);

    UserProfile toUserProfile(UserInfoRequest userInfoRequest);


    @Named("toRole")
    default Role toRole(String role) {
        return Role.builder().name(role).build();
    }
}
