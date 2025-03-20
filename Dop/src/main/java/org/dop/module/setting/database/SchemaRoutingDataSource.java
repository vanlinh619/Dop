package org.dop.module.setting.database;

import javax.sql.DataSource;

public interface SchemaRoutingDataSource extends DataSource {

    void addDataSource(String schema, DataSource dataSource);
}
