package org.dop.module.user.service;

import org.dop.module.common.pojo.response.PageResponse;
import org.dop.module.user.pojo.request.UserInfoRequest;
import org.dop.module.user.pojo.request.UserPageRequest;
import org.dop.module.user.pojo.response.UserInfoResponse;


public interface UserManagerService {

    /**
     * Create user primary by super account.
     */
    UserInfoResponse createUserPrimary(UserInfoRequest userInfoRequest);

    /**
     * Get all user info for manage page.
     */
    PageResponse<UserInfoResponse> listAllUserPage(UserPageRequest userPageRequest);
}
