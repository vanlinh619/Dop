package org.dop.service.user;

import lombok.extern.log4j.Log4j2;
import org.dop.module.exception.InternalErrorException;
import org.dop.pojo.error.DopError;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Log4j2
@Service
public class UserServiceImpl implements UserService {

    @Override
    public UUID getUserId(Authentication authentication) {
        String username = authentication.getName();
        try {
            return UUID.fromString(username);
        } catch (IllegalArgumentException e) {
            log.error("Invalid UUID format for username: {}", username, e);
            throw new InternalErrorException(DopError.ILLEGAL_ARGUMENT, "Cannot extract user ID from authentication");
        }
    }
}
