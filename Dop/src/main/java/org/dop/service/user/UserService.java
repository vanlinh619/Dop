package org.dop.service.user;

import org.springframework.security.core.Authentication;

import java.util.UUID;

public interface UserService {
    UUID getUserId(Authentication authentication);
}
