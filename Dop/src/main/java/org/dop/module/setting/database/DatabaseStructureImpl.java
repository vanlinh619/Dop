package org.dop.module.setting.database;

import lombok.RequiredArgsConstructor;
import org.dop.config.property.DopSettingProperties;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;

@Service
@RequiredArgsConstructor
public class DatabaseStructureImpl implements DatabaseStructure {

    private final DopSettingProperties dopSettingProperties;

    @Override
    public void generateJdbcSessionStructure(DataSource dataSource) {
        ResourceDatabasePopulator popular = new ResourceDatabasePopulator();
        String dataStructure = dopSettingProperties.getDatasource().locatorCreateTableStructure();
        popular.addScript(new ClassPathResource(dataStructure));
        popular.execute(dataSource);
    }

    @Override
    public void generateJdbcSchemaStructure(DataSource dataSource, String schema) {
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        String sql = String.format(dopSettingProperties.getDatasource().scriptInitSchemaStructure(), schema);
        jdbcTemplate.execute(sql);
    }
}
