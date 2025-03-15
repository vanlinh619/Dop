package org.dop.module.security.tenant.service;

import org.dop.module.security.authorizationserver.pojo.error.AuthorizationServerError;
import org.dop.module.security.authorizationserver.pojo.exception.NoIssuerFoundException;
import org.springframework.security.oauth2.server.authorization.context.AuthorizationServerContext;
import org.springframework.security.oauth2.server.authorization.context.AuthorizationServerContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
public class TenantServiceImpl implements TenantService {

    @Override
    public String getTenant() {
        AuthorizationServerContext context = AuthorizationServerContextHolder.getContext();
        if (context != null && StringUtils.hasText(context.getIssuer())) {
            return context.getIssuer();
        }
        throw new NoIssuerFoundException(
                AuthorizationServerError.ISSUER_NOT_FOUND.name(),
                "Not found issuer."
        );
    }
}
