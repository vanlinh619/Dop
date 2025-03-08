package org.dop.module.setting.repository;

import org.dop.module.setting.entity.Startup;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StartupRepository extends CrudRepository<Startup, Long> {
    Optional<Startup> findByNameAndSchema(String name, String schema);
}
