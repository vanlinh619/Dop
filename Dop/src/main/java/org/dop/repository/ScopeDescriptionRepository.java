package org.dop.repository;

import org.dop.entity.ScopeDescription;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ScopeDescriptionRepository extends JpaRepository<ScopeDescription, String> {
}
