package org.dop.repository;

import org.dop.entity.ClientRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ClientRoleRepository extends JpaRepository<ClientRole, Long> {

    @Query("""
    select r.id from ClientRole r where  r.client.clientId = :clientId and r.name = :role
    """)
    Optional<Long> findRoleIdByClientId(String clientId, String role);
}
