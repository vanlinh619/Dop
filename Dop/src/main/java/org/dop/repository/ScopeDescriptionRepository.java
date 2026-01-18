package org.dop.repository;

import org.dop.entity.ScopeDescription;
import org.dop.entity.state.LanguageCode;
import org.dop.module.security.pojo.projection.ScopeDescriptionProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface ScopeDescriptionRepository extends JpaRepository<ScopeDescription, Long> {

    @Query("""
            select new org.dop.module.security.pojo.projection.ScopeDescriptionProjection(sd.scope.id, sd.scope.name, sd.description)
            from ScopeDescription sd
            where sd.scope.id in :scopes
                and sd.language.id = :languageCode
            """)
    Set<ScopeDescriptionProjection> listDescriptionByScope(Iterable<String> scopes, LanguageCode languageCode);
}
