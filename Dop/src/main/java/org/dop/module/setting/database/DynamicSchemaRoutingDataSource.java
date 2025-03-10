package org.dop.module.setting.database;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

import javax.sql.DataSource;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class DynamicSchemaRoutingDataSource extends AbstractRoutingDataSource implements SchemaRoutingDataSource {

    private final Map<Object, Object> dataSourceMap = new ConcurrentHashMap<>();

    public DynamicSchemaRoutingDataSource() {
        setTargetDataSources(dataSourceMap);
    }

    @Override
    protected Object determineCurrentLookupKey() {
        return SchemaContext.getSchema();
    }

    @Override
    public void addDataSource(String schema, DataSource dataSource) {
        dataSourceMap.put(schema, dataSource);
        initialize();
    }
}
