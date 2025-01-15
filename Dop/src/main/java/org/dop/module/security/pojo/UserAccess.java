package org.dop.module.security.pojo;

import java.util.List;

public record UserAccess(
        String identifier,
        List<String> roles
) {}
