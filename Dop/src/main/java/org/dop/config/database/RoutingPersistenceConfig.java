package org.dop.config.database;

import jakarta.persistence.EntityManagerFactory;
import org.dop.config.property.DopSettingProperties;
import org.dop.module.setting.database.DynamicSchemaRoutingDataSource;
import org.dop.module.setting.service.DatasourceService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.*;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(
        basePackages = "org.dop.repository",
        transactionManagerRef = RoutingPersistenceConfig.ROUTING_TRANSACTION_MANAGER,
        entityManagerFactoryRef = RoutingPersistenceConfig.ROUTING_ENTITY_MANAGER_FACTORY
)
public class RoutingPersistenceConfig {
    public static final String ROUTING_TRANSACTION_MANAGER = "routingTransactionManager";
    public static final String ROUTING_ENTITY_MANAGER_FACTORY = "routingEntityManagerFactory";
    public static final String ROUTING_DATASOURCE = "routingDatasource";
    public static final String DEFAULT_DATASOURCE = "defaultDatasource";

    @Bean(name = DEFAULT_DATASOURCE)
    public DataSource defaultDataSource(
            DopSettingProperties dopSettingProperties,
            DatasourceService datasourceService
    ) {
        String schema = dopSettingProperties.getDatasource().getSchemaDefault();
        return datasourceService.newDatasource(schema);
    }

    @Primary
    @Bean(name = ROUTING_DATASOURCE)
    public DynamicSchemaRoutingDataSource routingDataSource(
            @Qualifier(DEFAULT_DATASOURCE) DataSource dataSource,
            DopSettingProperties dopSettingProperties
    ) {
        DynamicSchemaRoutingDataSource schemaRoutingDataSource = new DynamicSchemaRoutingDataSource();
        schemaRoutingDataSource.setDefaultTargetDataSource(dataSource);
        schemaRoutingDataSource.addSchema(dopSettingProperties.getDatasource().getSchemaDefault(), dataSource);
        return schemaRoutingDataSource;
    }

    @Primary
    @Bean(name = ROUTING_ENTITY_MANAGER_FACTORY)
    public LocalContainerEntityManagerFactoryBean routingEntityManager(
            DopSettingProperties dopSettingProperties,
            EntityManagerFactoryBuilder builder,
            DataSource dataSource
    ) {
        return builder
                .dataSource(dataSource)
                .packages("org.dop.entity")
                .properties(SettingPersistenceConfig.hibernateProperties(dopSettingProperties))
                .build();
    }

    @Primary
    @Bean(name = ROUTING_TRANSACTION_MANAGER)
    public PlatformTransactionManager routingTransactionManager(
            @Qualifier(ROUTING_ENTITY_MANAGER_FACTORY) EntityManagerFactory entityManagerFactory
    ) {
        return new JpaTransactionManager(entityManagerFactory);
    }
}
