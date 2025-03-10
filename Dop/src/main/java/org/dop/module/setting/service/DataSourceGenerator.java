package org.dop.module.setting.service;

import javax.sql.DataSource;

public interface DataSourceGenerator {
    DataSource newDatasource(String schema);
}
