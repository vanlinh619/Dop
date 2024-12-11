package org.dop.repository;

import org.dop.entity.UserPrimary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserPrimaryRepository extends JpaRepository<UserPrimary, Integer> {
}
