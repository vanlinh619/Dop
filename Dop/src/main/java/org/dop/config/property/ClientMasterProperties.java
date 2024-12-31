package org.dop.config.property;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * Default config for master client, which interact with authorization server to manage user
 * */
@Getter
@Setter
@Component
@ConfigurationProperties(prefix = "dop.manage.master.client")
public class ClientMasterProperties {

    private String redirectUrl;
    private String clientId = "master-client";
    private String scopeMaster = "master";
}
