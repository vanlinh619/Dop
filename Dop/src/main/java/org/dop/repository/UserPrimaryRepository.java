package org.dop.repository;

import org.dop.entity.UserPrimary;
import org.dop.module.manageuser.pojo.projection.Auth2UserAuthenticatedProjection;
import org.dop.module.manageuser.pojo.projection.EmailUserInfoProjection;
import org.dop.module.manageuser.pojo.projection.UserAuthenticatedProjection;
import org.dop.module.manageuser.pojo.projection.UserIdAndRoleProjection;
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
            select new org.dop.module.manageuser.pojo.projection.UserAuthenticatedProjection(u.id, u.password, u.status)
            from UserPrimary u
            where u.email.value = :identifier or u.username = :identifier
            """)
    Optional<UserAuthenticatedProjection> findUserAuthority(String identifier);

    @Query("""
            select new org.dop.module.manageuser.pojo.projection.UserAuthenticatedProjection(u.id, u.password, u.status)
            from UserPrimary u
            where u.username = cast(:identifier as string) or u.id = :identifier
            """)
    Optional<UserAuthenticatedProjection> findUserAuthority(UUID identifier);

    @Query("""
            select r.id
            from UserPrimary u
            join u.roles r
            where u.id = :userId
            """)
    Set<String> findRoles(UUID userId);

    @Query("""
            select new org.dop.module.manageuser.pojo.projection.Auth2UserAuthenticatedProjection(u.id, u.status)
            from UserPrimary u
            join UserProvider up on u.id = up.id
            where up.providerId = :subject
            """)
    Optional<Auth2UserAuthenticatedProjection> findAuth2User(String subject);

    @Query("""
            select new org.dop.module.manageuser.pojo.projection.EmailUserInfoProjection(
                u.email.value,
                u.email.verified
            )
            from UserPrimary u
            where u.id = :id
            """)
    EmailUserInfoProjection getEmailInfo(UUID id);

    @Query("""
            select
                new org.dop.module.manageuser.pojo.projection.UserIdAndRoleProjection(u.id, r.id)
            from UserPrimary u
            join u.roles as r
            where u.id in :ids
            """)
    List<UserIdAndRoleProjection> findAllIdAndRole(List<UUID> ids);
}
