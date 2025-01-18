package org.dop.config.property;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Component
@ConfigurationProperties(prefix = "dop.oauth2.authorization.key")
public class KeySourceProperties {
    /// Tenant identifier default
    private Long rsaKeySize = 2048L;
}
