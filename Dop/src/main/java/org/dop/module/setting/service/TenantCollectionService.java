package org.dop.module.setting.service;

import java.util.Set;

public interface TenantCollectionService {
    void save(String tenant);

    Set<String> getTenants();
}
