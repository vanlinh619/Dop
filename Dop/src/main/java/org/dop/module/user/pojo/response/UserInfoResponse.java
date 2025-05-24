package org.dop.module.user.pojo.response;

import com.querydsl.core.annotations.QueryProjection;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldNameConstants;
import org.dop.entity.embeded.EmailEmbedded;
import org.dop.entity.state.UserPrimaryStatus;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Data
@Builder
@FieldNameConstants
@AllArgsConstructor
@NoArgsConstructor
public class UserInfoResponse {
    private UUID id;
    private String fullName;
    private EmailEmbedded email;
    private UserPrimaryStatus status;
    private List<String> roles;
    private Instant createdDate;
    private Instant lastModifiedDate;

    @QueryProjection
    public UserInfoResponse(
            UUID id,
            String fullName,
            EmailEmbedded email,
            UserPrimaryStatus status,
            Instant createdDate,
            Instant lastModifiedDate) {

        this.id = id;
        this.fullName = fullName;
        this.email = email;
        this.status = status;
        this.createdDate = createdDate;
        this.lastModifiedDate = lastModifiedDate;
    }
}
