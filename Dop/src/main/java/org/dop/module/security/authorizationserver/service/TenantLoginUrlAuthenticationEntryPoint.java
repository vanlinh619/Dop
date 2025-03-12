package org.dop.module.security.authorizationserver.service;

import org.dop.module.setting.database.TenantContext;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;

public class TenantLoginUrlAuthenticationEntryPoint extends LoginUrlAuthenticationEntryPoint {
    /**
     * @param loginFormUrl URL where the login page can be found. Should either be
     *                     relative to the web-app context path (include a leading {@code /}) or an absolute
     *                     URL.
     */
    public TenantLoginUrlAuthenticationEntryPoint(String loginFormUrl) {
        super(loginFormUrl);
    }

    @Override
    public String getLoginFormUrl() {
        return String.format("/%s%s", TenantContext.getTenant(), super.getLoginFormUrl());
    }
}
