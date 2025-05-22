package org.dop.repository;

import org.dop.entity.QUserPrimary;
import org.dop.entity.QUserProfile;
import org.dop.entity.UserPrimary;
import org.dop.entity.state.UserPrimaryStatus;
import org.dop.module.user.pojo.response.UserInfoResponse;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface UserPrimaryDslRepository extends JpaRepository<UserPrimary, UUID>, DslRepository<UserPrimary> {

    default List<UserInfoResponse> listUserPage(String search, Pageable pageable) {
        QUserPrimary userPrimary = QUserPrimary.userPrimary;
        QUserProfile userProfile = QUserProfile.userProfile;

        var query = newQuery()
                .from(userPrimary)
                .innerJoin(userProfile)
                .on(userPrimary.id.eq(userProfile.id))
                .where(
                        userPrimary.status.eq(UserPrimaryStatus.ENABLED)
                )
                .orderBy(parseOrderSpecifiers(userPrimary, pageable))
                .limit(pageable.getPageSize())
                .offset(pageable.getOffset());

        return null;
    }
}
