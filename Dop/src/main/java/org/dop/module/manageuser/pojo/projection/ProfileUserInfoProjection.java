package org.dop.module.manageuser.pojo.projection;

import org.dop.entity.state.Gender;

import java.time.Instant;
import java.time.ZoneId;
import java.util.Locale;

public record ProfileUserInfoProjection(
        String fullName,
        String familyName,
        String middleName,
        String givenName,
        String picture,
        Gender gender,
        Instant birthDate,
        String profile,
        String website,
        Locale locale,
        ZoneId zoneInfo,
        Instant lastModifiedDate
) {
}
