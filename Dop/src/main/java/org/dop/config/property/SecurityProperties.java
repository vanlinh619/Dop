package org.dop.config.property;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;

@Getter
@Setter
@Component
@ConfigurationProperties(prefix = "dop.security")
public class SecurityProperties {
    private List<String> allowedOrigins;
    private String loginUrl = "/login";
}
