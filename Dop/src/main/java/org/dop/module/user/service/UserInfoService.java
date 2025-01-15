package org.dop.module.user.service;

import org.dop.module.user.pojo.request.UserInfoRequest;
import org.dop.module.user.pojo.response.UserInfoResponse;

public interface UserInfoService {
    /**
     * Create user primary by super account.
     */
    UserInfoResponse createUserPrimary(UserInfoRequest userInfoRequest);
}
