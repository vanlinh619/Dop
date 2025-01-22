package org.dop.repository;

import org.dop.entity.UserProfile;
import org.dop.module.user.pojo.projection.UserConsentProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface UserProfileRepository extends JpaRepository<UserProfile, Integer> {

    @Query("""
    select new org.dop.module.user.pojo.projection.UserConsentProjection(up.fullName)
    from UserProfile up
    where up.id = :id
    """)
    UserConsentProjection getUserConsentInfo(UUID id);
}
