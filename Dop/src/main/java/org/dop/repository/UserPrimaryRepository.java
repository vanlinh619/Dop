package org.dop.repository;

import org.dop.entity.UserPrimary;
import org.dop.module.user.pojo.projection.UserAuthorityProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserPrimaryRepository extends JpaRepository<UserPrimary, UUID> {

    @Query("""
    select new org.dop.module.user.pojo.projection.UserAuthorityProjection(u.id, u.password, u.status)
    from UserPrimary u
    where u.email.value = :identifier or u.username = :identifier
    """)
    Optional<UserAuthorityProjection> findUserAuthority(String identifier);

    @Query("""
    select new org.dop.module.user.pojo.projection.UserAuthorityProjection(u.id, u.password, u.status)
    from UserPrimary u
    where u.username = cast(:identifier as string) or u.id = :identifier
    """)
    Optional<UserAuthorityProjection> findUserAuthority(UUID identifier);

    @Query("""
    select u.id
    from UserPrimary u
    where u.email.value = :identifier or u.username = :identifier
    """)
    Optional<UUID> findUserIdByIdentifier(String identifier);

    @Query("""
    select r.id
    from UserPrimary u
    join u.roles r
    where u.id = :userId
    """)
    List<String> findRoles(UUID userId);
}
