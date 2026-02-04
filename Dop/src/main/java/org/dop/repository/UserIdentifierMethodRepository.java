package org.dop.repository;

import org.dop.entity.UserIdentifierMethod;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserIdentifierMethodRepository extends JpaRepository<UserIdentifierMethod, Integer> {
}
