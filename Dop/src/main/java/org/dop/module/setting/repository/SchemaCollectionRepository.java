package org.dop.module.setting.repository;

import org.dop.module.setting.entity.SchemaCollection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SchemaCollectionRepository extends JpaRepository<SchemaCollection, String> {
}
