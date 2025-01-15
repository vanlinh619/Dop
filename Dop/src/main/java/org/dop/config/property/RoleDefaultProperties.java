package org.dop.config.property;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.dop.config.SecurityConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Component
@ConfigurationProperties(prefix = "dop.manage.master.role")
public class RoleDefaultProperties {

    private String rolePrefix = "ROLE_";
    private String roleClaim = "role";

    private String roleSuper = "SUPER";
    private String roleUser = "USER";

    public String addPrefix(String role) {
        return rolePrefix + role;
    }
}
