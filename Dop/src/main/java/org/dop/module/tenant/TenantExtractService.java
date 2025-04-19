package org.dop.module.tenant;

import jakarta.annotation.Nullable;
import jakarta.servlet.http.HttpServletRequest;

public interface TenantExtractService {

    @Nullable
    String extractTenant(HttpServletRequest request);
}
