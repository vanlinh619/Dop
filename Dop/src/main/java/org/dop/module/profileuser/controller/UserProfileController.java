package org.dop.module.profileuser.controller;

import lombok.RequiredArgsConstructor;
import org.dop.module.profileuser.pojo.request.MyAccountRequest;
import org.dop.module.profileuser.pojo.response.MyAccountResponse;
import org.dop.module.profileuser.service.UserProfileService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * PROFILE USER <br>
 * Api used by user to manage their profile.
 */
@RestController
@RequestMapping("api/v1/manage/user-profile")
@RequiredArgsConstructor
public class UserProfileController {

    private final UserProfileService userProfileService;

    @GetMapping
    public MyAccountResponse getMyAccount(MyAccountRequest request) {
        return userProfileService.getMyAccount(request);
    }
}
