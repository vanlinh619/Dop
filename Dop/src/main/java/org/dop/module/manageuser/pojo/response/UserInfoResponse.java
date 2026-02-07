package org.dop.module.manageuser.pojo.response;

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
}
