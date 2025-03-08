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
    private DatasourceProperties datasource;
    private HibernateProperties hibernate;


    @Getter
    @Setter
    public static class DatasourceProperties {
        private String jdbcUrl;
        private String username;
        private String password;
        private String schemaPattern = "[a-z0-9_]+";
        private String database = "dop";
        private String schemaDefault = "dop";

        public String generateUrl(String schema) {
            return jdbcUrl.replaceAll("/[^/]+$", String.format("/%s?currentSchema=%s", database, schema));
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
