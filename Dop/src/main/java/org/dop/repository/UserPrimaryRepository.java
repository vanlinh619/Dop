package org.dop.repository;

import org.dop.entity.UserPrimary;
import org.dop.module.user.pojo.projection.Auth2UserCredentialProjection;
import org.dop.module.user.pojo.projection.UserCredentialProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

@Repository
public interface UserPrimaryRepository extends JpaRepository<UserPrimary, UUID> {

    @Query("""
    select new org.dop.module.user.pojo.projection.UserCredentialProjection(u.id, u.password, u.status)
    from UserPrimary u
    where u.email.value = :identifier or u.username = :identifier
    """)
    Optional<UserCredentialProjection> findUserAuthority(String identifier);

    @Query("""
    select new org.dop.module.user.pojo.projection.UserCredentialProjection(u.id, u.password, u.status)
    from UserPrimary u
    where u.username = cast(:identifier as string) or u.id = :identifier
    """)
    Optional<UserCredentialProjection> findUserAuthority(UUID identifier);

    @Query("""
    select r.id
    from UserPrimary u
    join u.roles r
    where u.id = :userId
    """)
    Set<String> findRoles(UUID userId);

    @Query("""
    select new org.dop.module.user.pojo.projection.Auth2UserCredentialProjection(u.id, u.status)
    from UserPrimary u
    join UserProvider up on u.id = up.id
    where up.providerId = :subject
    """)
    Optional<Auth2UserCredentialProjection> findAuth2User(String subject);
}
