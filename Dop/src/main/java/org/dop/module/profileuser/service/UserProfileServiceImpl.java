package org.dop.module.profileuser.service;

import org.dop.module.profileuser.pojo.request.MyAccountRequest;
import org.dop.module.profileuser.pojo.response.MyAccountResponse;
import org.springframework.stereotype.Service;

@Service
public class UserProfileServiceImpl implements UserProfileService {

    @Override
    public MyAccountResponse getMyAccount(MyAccountRequest request) {
        return null;
    }
}
