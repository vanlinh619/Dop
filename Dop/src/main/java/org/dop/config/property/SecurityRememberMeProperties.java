package org.dop.config.property;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Component
@ConfigurationProperties(prefix = "dop.security.remember-me")
public class SecurityRememberMeProperties {
    private boolean enable = true;
    private String key;
    private int expiresIn = 604800;
}
