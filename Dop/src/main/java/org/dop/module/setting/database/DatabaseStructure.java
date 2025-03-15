package org.dop.module.setting.database;

import javax.sql.DataSource;

public interface DatabaseStructure {

    void generateJdbcSessionStructure(DataSource dataSource);

    void generateJdbcSchemaStructure(DataSource dataSource, String schema);
}
