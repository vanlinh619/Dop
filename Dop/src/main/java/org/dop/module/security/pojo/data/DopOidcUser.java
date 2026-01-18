package org.dop.module.security.pojo.data;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.core.oidc.OidcIdToken;
import org.springframework.security.oauth2.core.oidc.OidcUserInfo;
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;

import java.util.Collection;

@JsonTypeInfo(use = JsonTypeInfo.Id.CLASS, include = JsonTypeInfo.As.PROPERTY, property = "@class")
public class DopOidcUser extends DefaultOidcUser {

    private final String userId;

    @JsonCreator
    public DopOidcUser(
            @JsonProperty("authorities") Collection<? extends GrantedAuthority> authorities,
            @JsonProperty("idToken") OidcIdToken idToken,
            @JsonProperty("userInfo") OidcUserInfo userInfo,
            @JsonProperty("userId") String userId
    ) {
        super(authorities, idToken, userInfo);
        this.userId = userId;
    }

    @Override
    public String getName() {
        return userId;
    }
}
