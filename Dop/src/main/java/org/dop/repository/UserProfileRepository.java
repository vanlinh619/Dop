package org.dop.repository;

import org.dop.entity.UserProfile;
import org.dop.module.user.pojo.projection.AddressUserInfoProjection;
import org.dop.module.user.pojo.projection.PhoneUserInfoProjection;
import org.dop.module.user.pojo.projection.ProfileUserInfoProjection;
import org.dop.module.user.pojo.projection.UserConsentProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface UserProfileRepository extends JpaRepository<UserProfile, UUID> {

    @Query("""
    select new org.dop.module.user.pojo.projection.UserConsentProjection(up.fullName)
    from UserProfile up
    where up.id = :id
    """)
    UserConsentProjection getUserConsentInfo(UUID id);

    @Query("""
    select new org.dop.module.user.pojo.projection.ProfileUserInfoProjection(
        up.fullName, up.familyName, up.middleName, up.givenName, up.picture, up.gender, up.birthDate, up.profile,
        up.website, up.locale, up.zoneInfo, up.lastModifiedDate
    )
    from UserProfile up
    where up.id = :id
    """)
    ProfileUserInfoProjection getProfileInfo(UUID id);

    @Query("""
    select new org.dop.module.user.pojo.projection.AddressUserInfoProjection(
        up.address.addressLine1
    )
    from UserProfile up
    where up.id = :id
    """)
    AddressUserInfoProjection getAddressInfo(UUID id);

    @Query("""
    select new org.dop.module.user.pojo.projection.PhoneUserInfoProjection(
        up.phone.number,
        up.phone.verified
    )
    from UserProfile up
    where up.id = :id
    """)
    PhoneUserInfoProjection getPhoneInfo(UUID id);
}
