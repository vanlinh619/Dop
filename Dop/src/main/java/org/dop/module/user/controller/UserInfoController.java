package org.dop.module.user.controller;

import lombok.RequiredArgsConstructor;
import org.dop.module.user.pojo.request.UserInfoRequest;
import org.dop.module.user.pojo.response.UserInfoResponse;
import org.dop.module.user.service.UserInfoService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@PreAuthorize("hasRole(@roleDefaultProperties.roleSuper) and hasAuthority(@clientMasterProperties.scopeMaster)")
@RequestMapping("/api/v1/manage/user-info")
@RequiredArgsConstructor
public class UserInfoController {

    private final UserInfoService userInfoService;


    @GetMapping
    public List<UserInfoResponse> getAllUser() {

        return List.of();
    }

    @PostMapping
    public UserInfoResponse createUser(UserInfoRequest userInfoRequest) {
        return userInfoService.createUserPrimary(userInfoRequest);
    }

}
