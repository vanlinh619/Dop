package org.dop.module.user.pojo.projection;

public record EmailUserInfoProjection(
        String email,
        Boolean verified
) {}
