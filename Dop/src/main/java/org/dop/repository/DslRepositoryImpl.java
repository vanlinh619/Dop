package org.dop.repository;

import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.EntityPathBase;
import com.querydsl.core.types.dsl.PathBuilder;
import com.querydsl.core.types.dsl.StringPath;
import com.querydsl.jpa.JPQLQuery;
import com.querydsl.jpa.impl.JPAQuery;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.dop.config.database.RoutingPersistenceConfig;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DslRepositoryImpl<T> implements DslRepository<T> {

    @Qualifier(RoutingPersistenceConfig.ROUTING_ENTITY_MANAGER_FACTORY)
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
    public OrderSpecifier<?>[] parseOrderSpecifiers(EntityPathBase<T> qEntity, Pageable pageable) {
        if (pageable.getSort().isEmpty()) {
            return new OrderSpecifier[0];
        }
        PathBuilder<T> pathBuilder = new PathBuilder<>(qEntity.getType(), qEntity.getMetadata());
        return pageable.getSort().stream()
                .map(order -> {
                    StringPath path = pathBuilder.getString(order.getProperty());
                    return order.isAscending() ? path.asc() : path.desc();
                })
                .toArray(OrderSpecifier[]::new);
    }
}
