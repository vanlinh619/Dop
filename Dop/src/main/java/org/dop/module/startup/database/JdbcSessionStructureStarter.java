package org.dop.module.startup.database;

import lombok.RequiredArgsConstructor;
import org.dop.config.database.RoutingPersistenceConfig;
import org.dop.config.property.DopSettingProperties;
import org.dop.module.setting.database.DatabaseStructure;
import org.dop.module.setting.database.TenantContext;
import org.dop.module.startup.Starter;
import org.dop.module.startup.StartupName;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;

@Service(StartupName.JDBC_SESSION_STRUCTURE)
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
@RequiredArgsConstructor
public class JdbcSessionStructureStarter implements Starter {

    private final DopSettingProperties dopSettingProperties;
    private final DatabaseStructure databaseStructure;
    private final @Qualifier(RoutingPersistenceConfig.ROUTING_DATASOURCE) DataSource dataSource;

    @Override
    public void start() {
        if (!dopSettingProperties.getDatasource().getSchemaDefault().equals(TenantContext.getTenant())) {
            databaseStructure.generateJdbcSessionStructure(dataSource);
        }
    }
}
