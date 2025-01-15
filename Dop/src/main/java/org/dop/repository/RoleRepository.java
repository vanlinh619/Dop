package org.dop.repository;

import org.dop.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, String> {

    @Query("""
    select r.id
    from Role r
    where r.id in :roles
    """)
    List<String> verifyRole(List<String> roles);
}
