package org.dop.config.property;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Component
@ConfigurationProperties(prefix = "dop.manage.master.role")
public class RoleDefaultProperties {

    private String roleAdmin = "ADMIN";
    private String roleUser = "USER";
}
