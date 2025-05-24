package org.dop.repository;

import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.StringPath;
import com.querydsl.jpa.JPQLQuery;
import com.querydsl.jpa.impl.JPAQuery;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@RequiredArgsConstructor
public class DslRepositoryImpl<T> implements DslRepository<T> {

    private final EntityManager entityManager;

    @Override
    public EntityManager getRoutingEntityManager() {
        return entityManager;
    }

    @Override
    public JPQLQuery<T> newQuery() {
        return new JPAQuery<>(getRoutingEntityManager());
    }

    @Override
    public OrderSpecifier<?>[] parseOrderSpecifiers(Sort sort, Map<String, StringPath> mapPath) {
        if (sort.isEmpty()) {
            return new OrderSpecifier[0];
        }

        return sort.stream()
                .map(order -> {
                    StringPath path = mapPath.get(order.getProperty());
                    return order.isAscending() ? path.asc() : path.desc();
                })
                .toArray(OrderSpecifier[]::new);
    }
}
