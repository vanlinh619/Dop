package org.dop.module.user.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.dop.module.common.pojo.response.PageResponse;
import org.dop.module.user.pojo.request.UserInfoRequest;
import org.dop.module.user.pojo.request.UserPageRequest;
import org.dop.module.user.pojo.response.UserInfoResponse;
import org.dop.module.user.service.UserManageService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.TimeUnit;

@RestController
@PreAuthorize("hasRole(@roleDefaultProperties.roleSuper) and hasAuthority(@clientMasterProperties.scopeMaster)")
@RequestMapping("*/api/v1/manage/user-info")
@RequiredArgsConstructor
public class UserInfoController {

    private final UserManageService UserManageService;


    @GetMapping
    public PageResponse<UserInfoResponse> getAllUser(@Valid UserPageRequest userPageRequest) throws InterruptedException {
        TimeUnit.SECONDS.sleep(5);
        return UserManageService.listAllUserPage(userPageRequest);
    }

    @PostMapping
    public UserInfoResponse createUser(@RequestBody @Valid UserInfoRequest userInfoRequest) {
        return UserManageService.createUserPrimary(userInfoRequest);
    }

}
