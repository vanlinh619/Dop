package org.dop.repository;

import org.dop.entity.UserPrimaryRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface UserPrimaryRoleRepository extends JpaRepository<UserPrimaryRole, Long> {

    @Query("""
    select upr.role.name from UserPrimaryRole upr where upr.user.id = :userId
    """)
    List<String> findRoles(UUID userId);
}
