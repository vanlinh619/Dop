package org.dop.config.database;

import jakarta.persistence.EntityManagerFactory;
import org.dop.config.property.DopSettingProperties;
import org.dop.module.setting.database.DynamicSchemaRoutingDataSource;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.*;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.util.Properties;

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

    @Primary
    @Bean(name = ROUTING_DATASOURCE)
    public DynamicSchemaRoutingDataSource routingDataSource(
            @Qualifier(SettingPersistenceConfig.SETTING_DATASOURCE) DataSource dataSource,
            DopSettingProperties dopSettingProperties
    ) {
        DynamicSchemaRoutingDataSource schemaRoutingDataSource = new DynamicSchemaRoutingDataSource();
        schemaRoutingDataSource.setDefaultTargetDataSource(dataSource);
        schemaRoutingDataSource.addSchema(dopSettingProperties.getSchemaDefault(), dataSource);
        return schemaRoutingDataSource;
    }


    @Primary
    @Bean(name = ROUTING_ENTITY_MANAGER_FACTORY)
    public LocalContainerEntityManagerFactoryBean routingEntityManager(
            DopSettingProperties dopSettingProperties,
            DataSource dataSource
    ) {
        LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
        em.setDataSource(dataSource);
        em.setPackagesToScan("org.dop.entity");
        HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        em.setJpaVendorAdapter(vendorAdapter);
        em.setJpaProperties(hibernateProperties(dopSettingProperties));
        return em;
    }

    private Properties hibernateProperties(DopSettingProperties dopSettingProperties) {
        Properties properties = new Properties();
        properties.put("hibernate.show_sql", dopSettingProperties.getHibernate().isShowSql());
        properties.put("hibernate.format_sql", dopSettingProperties.getHibernate().isFormatSql());
        properties.put("hibernate.hbm2ddl.auto", dopSettingProperties.getHibernate().getDdlAuto());
        return properties;
    }

    @Primary
    @Bean(name = ROUTING_TRANSACTION_MANAGER)
    public PlatformTransactionManager routingTransactionManager(
            @Qualifier(ROUTING_ENTITY_MANAGER_FACTORY) EntityManagerFactory entityManagerFactory
    ) {
        return new JpaTransactionManager(entityManagerFactory);
    }
}
