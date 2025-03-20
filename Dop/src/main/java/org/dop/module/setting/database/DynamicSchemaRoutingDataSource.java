package org.dop.module.setting.database;

import org.dop.module.tenant.context.TenantContext;
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
        return TenantContext.getTenant();
    }

    @Override
    public void addDataSource(String schema, DataSource dataSource) {
        dataSourceMap.put(schema, dataSource);
        /// Apply the new data source to the routing data source
        initialize();
    }
}
