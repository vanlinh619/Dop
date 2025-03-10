package org.dop.module.setting.service;

import javax.sql.DataSource;

public interface DataSourceService {

    void addDataSource(String schema);

    void addDataSource(String schema, DataSource dataSource);
}
