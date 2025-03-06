package org.dop.config.property;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Component
@ConfigurationProperties(prefix = "dop.setting")
public class DopSettingProperties {
    private String schema = "dop_setting";
    private String schemaDefault = "dop";
    private DatasourceProperties datasource;
    private String schemaPattern = "[a-z0-9-]+";
    private HibernateProperties hibernate;


    @Getter
    @Setter
    public static class DatasourceProperties {
        private String jdbcUrl;
        private String username;
        private String password;

        public String addSchema(String schema) {
            if (jdbcUrl.endsWith("/")) {
                return jdbcUrl + schema;
            }
            return String.format( "%s/%s", jdbcUrl, schema);
        }
    }

    @Getter
    @Setter
    public static class HibernateProperties {
        private String ddlAuto;
        private boolean showSql;
        private boolean formatSql;
    }
}
