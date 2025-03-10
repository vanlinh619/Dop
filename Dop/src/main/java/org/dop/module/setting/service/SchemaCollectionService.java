package org.dop.module.setting.service;

import java.util.Set;

public interface SchemaCollectionService {
    void save(String schema);

    Set<String> getSchemas();
}
