package org.dop.module.security.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.dop.module.setting.database.TenantContext;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

public class RoutingDataSourceRequestFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        try {
            String path = request.getRequestURI();
            String[] parts = path.split("/");
            if (parts.length > 1) {
                String tenantId = parts[1];
                TenantContext.setCurrent(tenantId);
            }
            filterChain.doFilter(request, response);
        } finally {
            TenantContext.clear();
        }
    }
}
