package org.dop.module.manageuser.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.dop.module.common.pojo.response.PageResponse;
import org.dop.module.manageuser.pojo.request.UserInfoRequest;
import org.dop.module.manageuser.pojo.request.UserPageRequest;
import org.dop.module.manageuser.pojo.response.UserInfoResponse;
import org.dop.module.manageuser.service.UserManagerService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

/**
 * MANAGE USER <br>
 * Api used by super account to manage user info.
 */
@RestController
@PreAuthorize("hasRole(@roleDefaultProperties.roleSuper) and hasAuthority(@clientMasterProperties.scopeMaster)")
@RequestMapping("api/v1/manage/user")
@RequiredArgsConstructor
public class UserManagerController {

    private final UserManagerService UserManagerService;


    @GetMapping
    public PageResponse<UserInfoResponse> getAllUser(@Valid UserPageRequest userPageRequest) throws InterruptedException {
        return UserManagerService.listAllUserPage(userPageRequest);
    }

    @PostMapping
    public UserInfoResponse createUser(@RequestBody @Valid UserInfoRequest userInfoRequest) {
        return UserManagerService.createUserPrimary(userInfoRequest);
    }

}
