package org.dop.module.setting.repository;

import org.dop.module.setting.entity.SchemaCollection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface SchemaCollectionRepository extends JpaRepository<SchemaCollection, String> {

    @Query("""
    select sc.id
    from SchemaCollection sc
    """)
    Set<String> getSchemas();
}
