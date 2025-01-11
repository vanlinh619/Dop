package org.dop.module.user.controller;

import org.dop.module.user.pojo.response.UserInfoResponse;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@PreAuthorize("hasRole(@roleDefaultProperties.rawRoleSuper) and hasAuthority(@clientMasterProperties.scopeMaster)")
@RequestMapping("/api/v1/manage/user-info")
public class UserInfoController {

    @GetMapping
    public List<UserInfoResponse> getAllUser() {

        return List.of();
    }

}
