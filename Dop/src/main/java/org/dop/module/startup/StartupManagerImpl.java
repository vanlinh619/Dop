package org.dop.module.startup;

import lombok.RequiredArgsConstructor;
import org.dop.config.database.SettingPersistenceConfig;
import org.dop.config.property.DopSettingProperties;
import org.dop.module.setting.entity.Startup;
import org.dop.module.setting.database.DynamicSchemaRoutingDataSource;
import org.dop.module.setting.database.SchemaContext;
import org.dop.module.setting.service.DatasourceService;
import org.dop.module.setting.repository.StartupRepository;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Scope;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
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
    private final EntityManagerFactoryBuilder entityManagerFactoryBuilder;
    private final DopSettingProperties dopSettingProperties;

    @Override
    public void startAll() {
        starters.stream()
                .sorted(Comparator.comparing(Starter::priority))
                .forEach((starter) -> {
                    if (startupRepository.findByNameAndSchema(starter.getName(), SchemaContext.getSchema()).isEmpty()) {
                        starter.start();
                        Startup startup = Startup.builder()
                                .name(starter.getName())
                                .schema(SchemaContext.getSchema())
                                .build();
                        startupRepository.save(startup);
                    }
                });
    }

    @Override
    public void startNewDatasource(String schema) {
        DataSource dataSource = datasourceService.newDatasource(schema);
        dynamicSchemaRoutingDataSource.addSchema(schema, dataSource);
        dynamicSchemaRoutingDataSource.initialize();
        LocalContainerEntityManagerFactoryBean em = entityManagerFactoryBuilder
                .dataSource(dataSource)
                .packages("org.dop.entity")
                .properties(SettingPersistenceConfig.hibernateProperties(dopSettingProperties))
                .build();
        em.afterPropertiesSet();
        SchemaContext.setSchema(schema);
        startAll();
    }

    @Override
    public void startDataDefault() {
        SchemaContext.setSchema(dopSettingProperties.getDatasource().getSchemaDefault());
        startAll();
    }

}
