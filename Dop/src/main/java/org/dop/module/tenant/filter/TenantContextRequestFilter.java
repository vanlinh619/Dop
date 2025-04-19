package org.dop.module.tenant.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.dop.module.setting.service.TenantCollectionService;
import org.dop.module.tenant.TenantExtractService;
import org.dop.module.tenant.context.TenantContext;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@RequiredArgsConstructor
public class TenantContextRequestFilter extends OncePerRequestFilter {

    private final TenantCollectionService tenantCollectionService;
    private final TenantExtractService tenantExtractService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        try {
            String tenantId = tenantExtractService.extractTenant(request);
            if (StringUtils.hasText(tenantId)) {
                if (tenantCollectionService.getTenants().contains(tenantId) || ByPassFilterUrl.whiteList.contains(tenantId)) {
                    TenantContext.setCurrent(tenantId);
                    filterChain.doFilter(request, response);
                    return;
                }
            }
            response.sendError(HttpServletResponse.SC_FORBIDDEN);
        } finally {
            TenantContext.clear();
        }
    }
}
