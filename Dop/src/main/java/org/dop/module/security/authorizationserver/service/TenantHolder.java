package org.dop.module.security.authorizationserver.service;

import java.util.Set;

public interface TenantHolder {
    Set<String> getTenants();
}
