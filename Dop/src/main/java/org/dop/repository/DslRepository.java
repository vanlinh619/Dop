package org.dop.repository;

import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.StringPath;
import com.querydsl.jpa.JPQLQuery;
import jakarta.persistence.EntityManager;
import org.springframework.data.domain.Sort;

import java.util.Map;

public interface DslRepository<T> {
    EntityManager getRoutingEntityManager();

    JPQLQuery<T> newQuery();

    OrderSpecifier<?>[] parseOrderSpecifiers(Sort sort, Map<String, StringPath> mapPath);
}
