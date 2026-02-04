package org.dop.module.profileuser.service;

import org.dop.module.profileuser.pojo.request.MyAccountRequest;
import org.dop.module.profileuser.pojo.response.MyAccountResponse;

public interface UserProfileService {

    MyAccountResponse getMyAccount(MyAccountRequest request);

}
