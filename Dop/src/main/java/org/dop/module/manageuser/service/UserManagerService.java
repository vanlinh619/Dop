package org.dop.module.manageuser.service;

import org.dop.module.common.pojo.response.PageResponse;
import org.dop.module.manageuser.pojo.request.UserInfoRequest;
import org.dop.module.manageuser.pojo.request.UserPageRequest;
import org.dop.module.manageuser.pojo.response.UserInfoResponse;


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
