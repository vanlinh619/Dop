package org.dop.repository;

import org.dop.entity.UserPrimary;
import org.dop.module.user.pojo.projection.UserAuthorityProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserPrimaryRepository extends JpaRepository<UserPrimary, Integer> {

    @Query("""
    select new org.dop.module.user.pojo.projection.UserAuthorityProjection(u.id, u.password, u.status)
    from UserPrimary u
    where u.email = :identifier or u.username = :identifier
    """)
    Optional<UserAuthorityProjection> findUserAuthority(String identifier);

    @Query("""
    select u.id
    from UserPrimary u
    where u.email.value = :identifier or u.username = :identifier
    """)
    Optional<UUID> findUserIdByIdentifier(String identifier);
}
