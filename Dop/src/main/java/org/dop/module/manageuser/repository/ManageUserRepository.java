package org.dop.module.manageuser.repository;

import jakarta.annotation.Nullable;
import org.dop.module.manageuser.pojo.projection.ManageUserProjection;
import org.dop.repository.UserPrimaryRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ManageUserRepository extends UserPrimaryRepository {

    @Query("""
            select new org.dop.module.manageuser.pojo.projection.ManageUserProjection(
                u.id,
                up.fullName as fullName,
                u.email.value as email,
                u.email.verified,
                u.status,
                u.createdDate,
                u.lastModifiedDate
            )
            from UserPrimary u
            join UserProfile up
                on u.id = up.id
            where
                (:search is null
                    or lower(up.fullName) like lower(concat('%', :search, '%'))
                    or lower(u.email.value) like lower(concat('%', :search, '%'))
                )
            """)
    Page<ManageUserProjection> getUserPage(@Nullable String search, Pageable pageable);
}
