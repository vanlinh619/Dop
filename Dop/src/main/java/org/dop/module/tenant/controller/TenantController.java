package org.dop.module.tenant.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.dop.module.setting.service.TenantCollectionService;
import org.dop.module.startup.StartupManager;
import org.dop.module.tenant.pojo.StartTenantRequest;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@PreAuthorize("hasRole(@roleDefaultProperties.roleSuper) and hasAuthority(@clientMasterProperties.scopeMaster)")
@RequestMapping("*/api/v1/manage/tenant")
@RequiredArgsConstructor
public class TenantController {

    private final StartupManager startupManager;
    private final TenantCollectionService tenantCollectionService;

    @GetMapping
    public Set<String> getTenant() {
        return tenantCollectionService.getTenants();
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public void startTenant(@Valid @RequestBody StartTenantRequest request) {
        startupManager.startNewTenant(request.getName());
    }
}
