package org.dop.repository;

import org.dop.entity.UserPrimaryClientRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserPrimaryClientRoleRepository extends JpaRepository<UserPrimaryClientRole, Long> {
}
