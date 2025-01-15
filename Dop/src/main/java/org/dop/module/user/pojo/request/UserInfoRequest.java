package org.dop.module.user.pojo.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldNameConstants;
import org.dop.entity.state.LanguageCode;
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

    /// Null value is allowed
    @Email
    private String email;

    /// Default value is "false"
    private Boolean emailVerified;

    /// Default value is "DISABLED"
    private UserPrimaryStatus status;

    /// Default value is "USER"
    private List<String> roles;

    @NotBlank
    private String givenName;

    /// Null value is allowed
    private String familyName;

    /// Null value is allowed
    private LanguageCode languageCode;
}
