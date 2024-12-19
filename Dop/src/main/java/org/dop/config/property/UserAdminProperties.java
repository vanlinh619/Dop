package org.dop.config.property;

import lombok.Getter;
import lombok.Setter;
import org.dop.entity.state.LanguageCode;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Component
@ConfigurationProperties(prefix = "dop.user.admin")
public class UserAdminProperties {
    private String username;
    private String password;
    private String email = "test.dipcode@gmail.com";
    private String name = "Admin";
    private LanguageCode languageCode = LanguageCode.vi;

}
