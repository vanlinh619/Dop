package org.dop.module.profileuser.repository;

import org.dop.module.profileuser.pojo.projection.MyAccountProjection;
import org.dop.repository.UserPrimaryRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface ProfileUserRepository extends UserPrimaryRepository {

    @Query("""
            select new org.dop.module.profileuser.pojo.projection.MyAccountProjection(
                up.fullName,
                case
                    when upd.provider = org.dop.entity.state.Provider.GOOGLE then u.email.value
                    else u.username
                end
            )
            from UserPrimary u
            inner join UserProfile up
                on u.id = up.id
            inner join UserProvider upd
                on u.id = upd.id
            where u.id = :id
            """)
    Optional<MyAccountProjection> findMyAccountById(UUID id);
}
