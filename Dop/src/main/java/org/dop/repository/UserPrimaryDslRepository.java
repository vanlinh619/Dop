package org.dop.repository;

import com.querydsl.jpa.JPQLQuery;
import jakarta.annotation.Nullable;
import org.dop.entity.QUserPrimary;
import org.dop.entity.QUserProfile;
import org.dop.entity.UserPrimary;
import org.dop.module.manageuser.pojo.response.QUserInfoResponse;
import org.dop.module.manageuser.pojo.response.UserInfoResponse;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@Repository
public interface UserPrimaryDslRepository extends JpaRepository<UserPrimary, UUID>, DslRepository<UserPrimary> {

    QUserPrimary userPrimary = QUserPrimary.userPrimary;
    QUserProfile userProfile = QUserProfile.userProfile;

    default List<UserInfoResponse> listUserPage(@Nullable String search, Pageable pageable) {

        JPQLQuery<UserInfoResponse> sql = newQuery()
                .select(new QUserInfoResponse(
                        userPrimary.id,
                        userProfile.fullName,
                        userPrimary.email,
                        userPrimary.status,
                        userPrimary.createdDate,
                        userPrimary.lastModifiedDate
                ))
                .from(userPrimary)
                .innerJoin(userProfile)
                .on(userPrimary.id.eq(userProfile.id));
        if (StringUtils.hasText(search)) {
            sql.where(userPrimary.email.value.likeIgnoreCase("%%%s%%".formatted(search))
                    .or(userProfile.fullName.likeIgnoreCase("%%%s%%".formatted(search)))
            );
        }
        sql
                .orderBy(parseOrderSpecifiers(pageable.getSort(), Map.of(
                        UserInfoResponse.Fields.fullName, userProfile.fullName,
                        UserInfoResponse.Fields.email, userPrimary.email.value
                )))
                .limit(pageable.getPageSize())
                .offset(pageable.getOffset());
        return sql.fetch();
    }

    default long countTotal(String search) {

        JPQLQuery<Long> sql = newQuery()
                .select(userPrimary.count())
                .from(userPrimary);
        if (StringUtils.hasText(search)) {
            sql
                    .innerJoin(userProfile)
                    .on(userPrimary.id.eq(userProfile.id))
                    .where(
                            userPrimary.email.value.likeIgnoreCase("%%%s%%".formatted(search))
                                    .or(userProfile.fullName.likeIgnoreCase("%%%s%%".formatted(search)))
                    );
        }
        return sql.fetchCount();
    }
}
