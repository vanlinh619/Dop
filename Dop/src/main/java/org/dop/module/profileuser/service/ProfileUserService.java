package org.dop.module.profileuser.service;

import org.dop.module.profileuser.pojo.request.MyAccountRequest;
import org.dop.module.profileuser.pojo.response.MyAccountResponse;

import java.util.UUID;

public interface ProfileUserService {

    MyAccountResponse getMyAccount(MyAccountRequest request, UUID userId);

}
