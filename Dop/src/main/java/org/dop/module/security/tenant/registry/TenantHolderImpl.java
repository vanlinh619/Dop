package org.dop.module.security.tenant.registry;

import lombok.RequiredArgsConstructor;
import org.dop.module.setting.service.SchemaCollectionService;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
@RequiredArgsConstructor
public class TenantHolderImpl implements TenantHolder {

    private final SchemaCollectionService schemaCollectionService;

    @Override
    public Set<String> getTenants() {
        return schemaCollectionService.getSchemas();
    }
}
