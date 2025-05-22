package org.dop.repository;

import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.EntityPathBase;
import com.querydsl.jpa.JPQLQuery;
import jakarta.persistence.EntityManager;
import org.springframework.data.domain.Pageable;

public interface DslRepository<T> {
    EntityManager getRoutingEntityManager();

    JPQLQuery<T> newQuery();

    OrderSpecifier<?>[] parseOrderSpecifiers(EntityPathBase<T> qEntity, Pageable pageable);
}
