package org.dop.module.tenant.service;

import jakarta.annotation.Nullable;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.dop.module.setting.repository.TenantCollectionRepository;
import org.springframework.stereotype.Service;

import java.util.List;

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
