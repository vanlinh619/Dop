package org.dop.config.property;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
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

    public boolean anySocialEnable() {
        return socials.entrySet().stream()
                .peek(socialPropertiesEntry -> {
                    if (Arrays.stream(SocialProvider.values()).noneMatch(socialProvider -> socialProvider
                            .getProvider().equals(socialPropertiesEntry.getKey()))) {
                        throw new SocialNotSupportedException("Social provider not supported.");
                    }
                })
                .anyMatch(socialPropertiesEntry -> socialPropertiesEntry.getValue().isEnable());
    }

    @Getter
    @Setter
    public static class SocialProperties {
        private boolean enable;
        private String clientId;
        private String clientSecret;
    }

    @Getter
    @AllArgsConstructor
    public enum SocialProvider {
        GOOGLE("google");

        private final String provider;
    }
}
