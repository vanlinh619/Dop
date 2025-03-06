package org.dop.module.startup;

import lombok.RequiredArgsConstructor;
import org.dop.entity.Startup;
import org.dop.module.setting.database.DynamicSchemaRoutingDataSource;
import org.dop.module.setting.database.SchemaContext;
import org.dop.module.setting.service.DatasourceService;
import org.dop.repository.StartupRepository;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.util.*;

@Service
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
@RequiredArgsConstructor
public class StartupManagerImpl implements StartupManager {

    private final List<Starter> starters;
    private final StartupRepository startupRepository;
    private final DatasourceService datasourceService;
    private final DynamicSchemaRoutingDataSource dynamicSchemaRoutingDataSource;

    @Override
    public void startAll() {
        starters.stream()
                .sorted(Comparator.comparing(Starter::priority))
                .forEach((starter) -> {
                    if (startupRepository.findByName(starter.getName()).isEmpty()) {
                        starter.start();
                        Startup startup = Startup.builder()
                                .name(starter.getName())
                                .build();
                        startupRepository.save(startup);
                    }
                });
    }

    @Override
    public void startNewDatasource(String schema) {
        SchemaContext.setSchema(schema);
        DataSource dataSource = datasourceService.newDatasource(schema);
        dynamicSchemaRoutingDataSource.addSchema(schema, dataSource);
        startAll();
    }

}
