package org.dop.module.user.pojo.projection;

public record PhoneUserInfoProjection(
        String phone,
        Boolean verified
) {}
