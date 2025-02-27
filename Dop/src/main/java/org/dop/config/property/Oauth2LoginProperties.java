package org.dop.config.property;

import lombok.Getter;
import lombok.Setter;
import org.dop.entity.state.Provider;
import org.dop.module.exception.security.SocialNotSupportedException;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Map;

@Getter
@Setter
@Component
@ConfigurationProperties(prefix = "dop.oauth2.login")
public class Oauth2LoginProperties {

    private Map<String, SocialProperties> socials;

    private String googleAuthorizationEndpoint = "/oauth2/authorization/google";

    public boolean anySocialEnable() {
        if (socials == null) {
            return false;
        }
        return socials.entrySet().stream()
                .peek(socialPropertiesEntry -> {
                    if (Arrays.stream(Provider.values()).noneMatch(socialProvider -> socialProvider
                            .getProvider().equals(socialPropertiesEntry.getKey()))) {
                        throw new SocialNotSupportedException("Social provider not supported.");
                    }
                })
                .anyMatch(socialPropertiesEntry -> socialPropertiesEntry.getValue().isEnable());
    }

    public boolean isSocialEnable(Provider socialProvider) {
        if (socials == null) {
            return false;
        }

        SocialProperties socialProperties = socials.get(socialProvider.getProvider());
        return socialProperties != null && socialProperties.isEnable();
    }

    @Getter
    @Setter
    public static class SocialProperties {
        private boolean enable;
        private String clientId;
        private String clientSecret;
    }
}
