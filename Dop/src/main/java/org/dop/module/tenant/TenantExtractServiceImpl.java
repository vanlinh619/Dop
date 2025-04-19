package org.dop.module.tenant;

import jakarta.annotation.Nullable;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Service;

@Service
public class TenantExtractServiceImpl implements TenantExtractService {

    @Nullable
    @Override
    public String extractTenant(HttpServletRequest request) {
        String path = request.getRequestURI();
        String[] parts = path.split("/");
        if (parts.length > 1) {
            return parts[1];
        }
        return null;
    }
}
