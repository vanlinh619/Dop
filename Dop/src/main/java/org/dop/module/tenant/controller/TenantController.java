package org.dop.module.tenant.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.dop.module.startup.StartupManager;
import org.dop.module.tenant.pojo.StartTenantRequest;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@PreAuthorize("hasRole(@roleDefaultProperties.roleSuper) and hasAuthority(@clientMasterProperties.scopeMaster)")
@RequestMapping("{issuer}/api/v1/manage/tenant")
@RequiredArgsConstructor
public class TenantController {

    private final StartupManager startupManager;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public void startTenant(@Valid StartTenantRequest request, @PathVariable String issuer) {
        startupManager.startNewTenant(request.getName());
    }
}
