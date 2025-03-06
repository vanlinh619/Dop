package org.dop.module.setting.service;

import javax.sql.DataSource;

public interface DatasourceService {

    DataSource newDatasource(String schema);
}
