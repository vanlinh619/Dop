package org.dop.module.startup;

public interface StartupManager {
    void startAll();

    void startNewDatasource(String schema);
}
