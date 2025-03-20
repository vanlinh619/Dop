package org.dop.module.setting.database;

import javax.sql.DataSource;

public interface DataSourceGenerator {
    DataSource newDatasource(String schema);
}
