package org.dop.config.property;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Component
@ConfigurationProperties(prefix = "dop.manage.master.client")
public class ClientMasterProperties {

    private String redirectUrl;
    private String clientId = "master-client";
    private String roleMaster = "MASTER";
    private String roleAdmin = "ADMIN";
    private String roleUser = "USER";
}
