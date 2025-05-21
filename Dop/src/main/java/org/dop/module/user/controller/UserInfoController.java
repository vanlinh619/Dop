package org.dop.module.user.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.dop.module.user.pojo.request.UserInfoRequest;
import org.dop.module.user.pojo.request.UserPageRequest;
import org.dop.module.user.pojo.response.UserInfoResponse;
import org.dop.module.user.service.UserInfoService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@PreAuthorize("hasRole(@roleDefaultProperties.roleSuper) and hasAuthority(@clientMasterProperties.scopeMaster)")
@RequestMapping("*/api/v1/manage/user-info")
@RequiredArgsConstructor
public class UserInfoController {

    private final UserInfoService userInfoService;


    @GetMapping
    public List<UserInfoResponse> getAllUser(UserPageRequest userPageRequest) {

        return userInfoService.listUserPage(userPageRequest);
    }

    @PostMapping
    public UserInfoResponse createUser(@RequestBody @Valid UserInfoRequest userInfoRequest) {
        return userInfoService.createUserPrimary(userInfoRequest);
    }

}
