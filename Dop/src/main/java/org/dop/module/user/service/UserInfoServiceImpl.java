package org.dop.module.user.service;

import lombok.RequiredArgsConstructor;
import org.dop.entity.UserPrimary;
import org.dop.entity.UserProfile;
import org.dop.module.user.mapper.UserInfoMapper;
import org.dop.module.user.pojo.request.UserInfoRequest;
import org.dop.module.user.pojo.response.UserInfoResponse;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserInfoServiceImpl implements UserInfoService {

    private final UserInfoMapper userInfoMapper;


    @Override
    public UserInfoResponse createUserPrimary(UserInfoRequest userInfoRequest) {
        UserPrimary userPrimary = userInfoMapper.toUserPrimary(userInfoRequest);
        UserProfile userProfile = userInfoMapper.toUserProfile(userInfoRequest);
        return null;
    }
}
