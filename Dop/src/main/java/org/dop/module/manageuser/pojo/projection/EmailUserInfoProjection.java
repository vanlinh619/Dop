package org.dop.module.manageuser.pojo.projection;

public record EmailUserInfoProjection(
        String email,
        Boolean verified
) {
}
