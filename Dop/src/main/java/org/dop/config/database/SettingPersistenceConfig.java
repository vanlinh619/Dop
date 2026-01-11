package org.dop.config.database;

import jakarta.persistence.EntityManagerFactory;
import org.dop.config.property.DopSettingProperties;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.boot.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(
        basePackages = "org.dop.module.setting.repository",
        transactionManagerRef = SettingPersistenceConfig.SETTING_TRANSACTION_MANAGER,
        entityManagerFactoryRef = SettingPersistenceConfig.SETTING_ENTITY_MANAGER_FACTORY
)
public class SettingPersistenceConfig {
    public static final String SETTING_TRANSACTION_MANAGER = "settingTransactionManager";
    public static final String SETTING_ENTITY_MANAGER_FACTORY = "settingEntityManagerFactory";
    public static final String SETTING_DATASOURCE = "settingDatasource";

    @Bean(name = SETTING_DATASOURCE)
    @ConfigurationProperties(prefix = "dop.setting.datasource")
    public DataSource settingDataSource() {
        return DataSourceBuilder.create().build();
    }

    @Bean(name = SETTING_ENTITY_MANAGER_FACTORY)
    public LocalContainerEntityManagerFactoryBean settingEntityManagerFactory(
            DopSettingProperties dopSettingProperties,
            @Qualifier(SETTING_DATASOURCE) DataSource dataSource,
            EntityManagerFactoryBuilder builder
    ) {

        return builder
                .dataSource(dataSource)
                .packages("org.dop.module.setting.entity")
                .properties(hibernateProperties(dopSettingProperties))
                .build();
    }

    public static Map<String, ?> hibernateProperties(DopSettingProperties dopSettingProperties) {
        Map<String, Object> properties = new HashMap<>();
        properties.put("hibernate.show_sql", dopSettingProperties.getHibernate().isShowSql());
        properties.put("hibernate.format_sql", dopSettingProperties.getHibernate().isFormatSql());
        properties.put("hibernate.hbm2ddl.auto", dopSettingProperties.getHibernate().getDdlAuto());
//        properties.put("open-in-view", false);
        return properties;
    }

    @Bean(name = SETTING_TRANSACTION_MANAGER)
    public PlatformTransactionManager settingTransactionManager(
            @Qualifier(SETTING_ENTITY_MANAGER_FACTORY) EntityManagerFactory searchEntityManagerFactory
    ) {
        return new JpaTransactionManager(searchEntityManagerFactory);
    }
}
