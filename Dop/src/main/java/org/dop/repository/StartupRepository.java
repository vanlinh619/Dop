package org.dop.repository;

import org.dop.entity.Startup;
import org.dop.entity.state.StartupName;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StartupRepository extends CrudRepository<Startup, Long> {
    Optional<Startup> findByName(StartupName name);
}
