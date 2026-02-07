package org.dop.module.profileuser.controller;

import lombok.RequiredArgsConstructor;
import org.dop.module.profileuser.pojo.request.MyAccountRequest;
import org.dop.module.profileuser.pojo.response.MyAccountResponse;
import org.dop.module.profileuser.service.ProfileUserService;
import org.dop.service.user.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;


/**
 * PROFILE USER <br>
 * Api used by user to manage their profile.
 */
@RestController
@RequestMapping("api/v1/user")
@RequiredArgsConstructor
public class ProfileUserController {

    private final UserService userService;
    private final ProfileUserService profileUserService;

    @GetMapping("my-account")
    public MyAccountResponse getMyAccount(MyAccountRequest request, Authentication authentication) {
        UUID userId = userService.getUserId(authentication);
        return profileUserService.getMyAccount(request, userId);
    }
}
