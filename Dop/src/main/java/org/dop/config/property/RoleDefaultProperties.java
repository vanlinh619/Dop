package org.dop.config.property;

import lombok.Setter;
import org.dop.config.SecurityConfig;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Setter
@Component
@ConfigurationProperties(prefix = "dop.manage.master.role")
public class RoleDefaultProperties {

    private String roleAdmin = "ADMIN";
    private String roleUser = "USER";

    public String getRoleAdmin() {
        return SecurityConfig.ROLE_PREFIX + roleAdmin;
    }

    public String getRoleUser() {
        return SecurityConfig.ROLE_PREFIX + roleUser;
    }
}
