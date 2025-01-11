package org.dop.config.property;

import lombok.Setter;
import org.dop.config.SecurityConfig;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Setter
@Component
@ConfigurationProperties(prefix = "dop.manage.master.role")
public class RoleDefaultProperties {

    private String roleSuper = "SUPER";
    private String roleUser = "USER";

    public String getRawRoleSuper() {
        return roleSuper;
    }

    public String getRoleSuper() {
        return SecurityConfig.ROLE_PREFIX + roleSuper;
    }

    public String getRoleUser() {
        return SecurityConfig.ROLE_PREFIX + roleUser;
    }
}
