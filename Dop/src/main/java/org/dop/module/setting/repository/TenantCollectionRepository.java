package org.dop.module.setting.repository;

import org.dop.module.setting.entity.TenantCollection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface TenantCollectionRepository extends JpaRepository<TenantCollection, String> {

    @Query("""
    select sc.id
    from TenantCollection sc
    """)
    Set<String> getSchemas();
}
