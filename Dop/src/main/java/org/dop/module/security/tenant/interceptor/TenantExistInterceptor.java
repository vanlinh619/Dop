package org.dop.module.security.tenant.interceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.dop.module.setting.service.SchemaCollectionService;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.HandlerInterceptor;


@RequiredArgsConstructor
public class TenantExistInterceptor implements HandlerInterceptor {

    private final SchemaCollectionService schemaCollectionService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        String path = request.getRequestURI();
        String[] parts = path.split("/");
        if (parts.length > 1) {
            String tenantId = parts[1];
            if (schemaCollectionService.getSchemas().contains(tenantId)) {
                return true;
            }
        }

        throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Request is forbidden.");
    }
}
