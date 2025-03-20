package org.dop.module.startup;

public interface StartupManager {
    void startAll();

    void startNewTenant(String schema);

    void startTenantDefault();
}
