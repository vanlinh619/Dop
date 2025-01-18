package org.dop.repository;

import org.dop.entity.KeySource;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface KeySourceRepository extends JpaRepository<KeySource, Long> {
}
