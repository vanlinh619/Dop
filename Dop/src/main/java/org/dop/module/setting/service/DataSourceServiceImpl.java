package org.dop.module.setting.service;

import lombok.RequiredArgsConstructor;
import org.dop.config.property.DopSettingProperties;
import org.dop.module.setting.database.DynamicSchemaRoutingDataSource;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;

@Service
@RequiredArgsConstructor
public class DataSourceServiceImpl implements DataSourceService {

    private final DopSettingProperties dopSettingProperties;
    private final DynamicSchemaRoutingDataSource schemaRoutingDataSource;


    @Override
    public void addDataSource(String schema) {
        DataSource dataSource = DataSourceBuilder.create()
                .url(dopSettingProperties.getDatasource().generateUrl(schema))
                .username(dopSettingProperties.getDatasource().getUsername())
                .password(dopSettingProperties.getDatasource().getPassword())
                .build();

        addDataSource(schema, dataSource);
    }

    @Override
    public void addDataSource(String schema, DataSource dataSource) {
        schemaRoutingDataSource.addDataSource(schema, dataSource);
    }
}
