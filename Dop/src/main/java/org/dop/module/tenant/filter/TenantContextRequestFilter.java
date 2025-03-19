package org.dop.module.tenant.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.dop.module.tenant.context.TenantContext;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

public class TenantContextRequestFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        try {
            String path = request.getRequestURI();
            String[] parts = path.split("/");
            String tenantId = "";
            if (parts.length > 1) {
                tenantId = parts[1];
            }
            TenantContext.setCurrent(tenantId);
            filterChain.doFilter(request, response);
        } finally {
            TenantContext.clear();
        }
    }
}
