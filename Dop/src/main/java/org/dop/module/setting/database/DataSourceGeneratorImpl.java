package org.dop.module.setting.database;

import lombok.RequiredArgsConstructor;
import org.dop.config.database.SettingPersistenceConfig;
import org.dop.config.property.DopSettingProperties;
import org.dop.module.setting.exception.SchemaNameException;
import org.dop.module.setting.pojo.error.DopSettingError;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;

@Service
@RequiredArgsConstructor
public class DataSourceGeneratorImpl implements DataSourceGenerator {

    private final DopSettingProperties dopSettingProperties;
    private final @Qualifier(SettingPersistenceConfig.SETTING_DATASOURCE) DataSource dataSource;
    private final DatabaseStructure databaseStructure;

    @Override
    public DataSource newDatasource(String schema) {
        if (!schema.matches(dopSettingProperties.getDatasource().getSchemaPattern())) {
            throw new SchemaNameException(DopSettingError.SCHEMA_MISMATCH.name(), "Schema mismatch pattern.");
        }

        databaseStructure.generateJdbcSchemaStructure(dataSource, schema);

        return DataSourceBuilder.create()
                .url(dopSettingProperties.getDatasource().generateUrl(schema))
                .username(dopSettingProperties.getDatasource().getUsername())
                .password(dopSettingProperties.getDatasource().getPassword())
                .build();
    }
}
