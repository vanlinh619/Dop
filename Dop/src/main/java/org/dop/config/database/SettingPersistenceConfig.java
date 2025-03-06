package org.dop.config.database;

import com.zaxxer.hikari.HikariDataSource;
import jakarta.persistence.EntityManagerFactory;
import org.dop.config.property.DopSettingProperties;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
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
    public DataSource searchDataSource(DopSettingProperties dopSettingProperties) {
        return DataSourceBuilder.create()
                .url(dopSettingProperties.getDatasource().addSchema(dopSettingProperties.getSchema()))
                .username(dopSettingProperties.getDatasource().getUsername())
                .password(dopSettingProperties.getDatasource().getPassword())
                .build();
    }

    @Bean(name = SETTING_ENTITY_MANAGER_FACTORY)
    public LocalContainerEntityManagerFactoryBean searchEntityManagerFactory(
            DopSettingProperties dopSettingProperties,
            @Qualifier(SETTING_DATASOURCE) DataSource dataSource
    ) {

        LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
        em.setDataSource(dataSource);
        em.setPackagesToScan("org.dop.module.setting.entity");
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

    @Bean(name = SETTING_TRANSACTION_MANAGER)
    public PlatformTransactionManager searchTransactionManager(
            @Qualifier(SETTING_ENTITY_MANAGER_FACTORY) EntityManagerFactory searchEntityManagerFactory
    ) {
        return new JpaTransactionManager(searchEntityManagerFactory);
    }
}
