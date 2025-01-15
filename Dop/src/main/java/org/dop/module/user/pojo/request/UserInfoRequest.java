package org.dop.module.user.pojo.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldNameConstants;
import org.dop.entity.embeded.EmailEmbedded;
import org.dop.entity.state.UserPrimaryStatus;

import java.util.List;

@Data
@FieldNameConstants
@NoArgsConstructor
@AllArgsConstructor
public class UserInfoRequest {

    @NotBlank
    @Pattern(regexp = "^[a-zA-Z0-9-]{4,50}$")
    private String username;

    private EmailEmbedded email;

    private UserPrimaryStatus status;

    private List<String> roles;

    private String firstName;
    private String lastName;
}
