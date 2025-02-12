package org.dop.module.security.login.pojo;

import java.util.List;

public record UserAccess(
        String identifier,
        List<String> roles
) {}
