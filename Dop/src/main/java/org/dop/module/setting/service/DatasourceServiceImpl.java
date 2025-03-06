package org.dop.module.setting.service;

import lombok.RequiredArgsConstructor;
import org.dop.config.property.DopSettingProperties;
import org.dop.module.setting.exception.SchemaNameException;
import org.dop.module.setting.pojo.error.DopSettingError;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;

@Service
@RequiredArgsConstructor
public class DatasourceServiceImpl implements DatasourceService {

    private final DopSettingProperties dopSettingProperties;
    private final JdbcTemplate jdbcTemplate;


    @Override
    public DataSource newDatasource(String schema) {
        if (!schema.matches(dopSettingProperties.getSchemaPattern())) {
            throw new SchemaNameException(DopSettingError.SCHEMA_MISMATCH.name(), "Schema mismatch pattern.");
        }

        String sql = String.format("CREATE SCHEMA IF NOT EXISTS %s", schema);
        jdbcTemplate.execute(sql);

        return DataSourceBuilder.create()
                .url(dopSettingProperties.getDatasource().addSchema(schema))
                .username(dopSettingProperties.getDatasource().getUsername())
                .password(dopSettingProperties.getDatasource().getPassword())
                .build();
    }
}
