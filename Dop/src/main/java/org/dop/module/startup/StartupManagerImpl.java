package org.dop.module.startup;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.dop.config.database.SettingPersistenceConfig;
import org.dop.config.property.DopSettingProperties;
import org.dop.module.setting.database.DataSourceGenerator;
import org.dop.module.setting.entity.Startup;
import org.dop.module.setting.repository.StartupRepository;
import org.dop.module.setting.service.DataSourceService;
import org.dop.module.setting.service.SchemaCollectionService;
import org.dop.module.tenant.context.TenantContext;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Scope;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.util.Comparator;
import java.util.List;
import java.util.Set;

@Service
@Log4j2
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
@RequiredArgsConstructor
public class StartupManagerImpl implements StartupManager {

    private final List<Starter> starters;
    private final StartupRepository startupRepository;
    private final DataSourceGenerator dataSourceGenerator;
    private final DataSourceService datasourceService;
    private final EntityManagerFactoryBuilder entityManagerFactoryBuilder;
    private final DopSettingProperties dopSettingProperties;
    private final SchemaCollectionService schemaCollectionService;

    @Override
    public void startAll() {
        starters.stream()
                .sorted(Comparator.comparing(Starter::priority))
                .forEach((starter) -> {
                    if (starter.alwaysStart()) {
                        starter.start();
                    } else if (startupRepository.findByNameAndSchema(starter.getName(), TenantContext.getTenant()).isEmpty()) {
                        log.info("Startup with name {}, schema {}", starter.getName(), TenantContext.getTenant());
                        starter.start();
                        Startup startup = Startup.builder()
                                .name(starter.getName())
                                .schema(TenantContext.getTenant())
                                .priority(starter.priority())
                                .build();
                        startupRepository.save(startup);
                    }
                });
    }

    @Override
    public void startNewDatasource(String schema) {
        DataSource dataSource = dataSourceGenerator.newDatasource(schema);
        /// Init entity manager factory to create tables
        LocalContainerEntityManagerFactoryBean em = entityManagerFactoryBuilder
                .dataSource(dataSource)
                .packages("org.dop.entity")
                .properties(SettingPersistenceConfig.hibernateProperties(dopSettingProperties))
                .build();
        em.afterPropertiesSet();
        datasourceService.addDataSource(schema, dataSource);
        /// Change schema context and start all data
        TenantContext.setCurrent(schema);
        startAll();
        schemaCollectionService.save(schema);
        TenantContext.clear();
    }

    @Override
    public void startDataDefault() {
        /// Init data source
        Set<String> schemas = schemaCollectionService.getSchemas();
        String schemaDefault = dopSettingProperties.getDatasource().getSchemaDefault();
        schemas.add(schemaDefault);
        schemas.forEach(schema -> {
            if (!schema.equals(schemaDefault)) {
                datasourceService.addDataSource(schema);
            }
            /// Change schema context and start all data
            TenantContext.setCurrent(schema);
            startAll();
            schemaCollectionService.save(schema);
            TenantContext.clear();
        });
    }

}
