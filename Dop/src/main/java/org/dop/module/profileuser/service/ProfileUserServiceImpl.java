package org.dop.module.profileuser.service;

import lombok.RequiredArgsConstructor;
import org.dop.module.exception.InternalErrorException;
import org.dop.module.profileuser.pojo.error.ProfileUserError;
import org.dop.module.profileuser.pojo.projection.MyAccountProjection;
import org.dop.module.profileuser.pojo.request.MyAccountRequest;
import org.dop.module.profileuser.pojo.response.MyAccountResponse;
import org.dop.module.profileuser.repository.ProfileUserRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ProfileUserServiceImpl implements ProfileUserService {

    private final ProfileUserRepository profileUserRepository;

    @Override
    public MyAccountResponse getMyAccount(MyAccountRequest request, UUID userId) {
        Optional<MyAccountProjection> myAccountOp = profileUserRepository.findMyAccountById(userId);
        if (myAccountOp.isEmpty()) {
            throw new InternalErrorException(ProfileUserError.CANNOT_FIND_ACCOUNT, "Cannot find user account for id: " + userId);
        }

        MyAccountProjection myAccount = myAccountOp.get();
        return MyAccountResponse.builder()
                .fullName(myAccount.fullName())
                .identifier(myAccount.identifier())
                .build();
    }
}
