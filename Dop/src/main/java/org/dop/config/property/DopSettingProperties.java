package org.dop.config.property;

import lombok.Getter;
import lombok.Setter;
import org.dop.module.exception.DopException;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.Map;

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
        private final Map<String, String> createTableStructure = Map.of(
                "jdbc:db2:", "org/springframework/session/jdbc/schema-db2.sql",
                "jdbc:derby:", "org/springframework/session/jdbc/schema-derby.sql",
                "jdbc:h2:", "org/springframework/session/jdbc/schema-h2.sql",
                "jdbc:hsqldb:", "org/springframework/session/jdbc/schema-hsqldb.sql",
                "jdbc:mysql:", "org/springframework/session/jdbc/schema-mysql.sql",
                "jdbc:oracle:", "org/springframework/session/jdbc/schema-oracle.sql",
                "jdbc:postgresql:", "org/springframework/session/jdbc/schema-postgresql.sql",
                "jdbc:sqlite:", "org/springframework/session/jdbc/schema-sqlite.sql",
                "jdbc:sqlserver:", "org/springframework/session/jdbc/schema-sqlserver.sql",
                "jdbc:sybase:", "org/springframework/session/jdbc/schema-sybase.sql"
        );
        private final Map<String, String> dropTableStructure = Map.of(
                "jdbc:db2:", "org/springframework/session/jdbc/schema-drop-db2.sql",
                "jdbc:derby:", "org/springframework/session/jdbc/schema-drop-derby.sql",
                "jdbc:h2:", "org/springframework/session/jdbc/schema-drop-h2.sql",
                "jdbc:hsqldb:", "org/springframework/session/jdbc/schema-drop-hsqldb.sql",
                "jdbc:mysql:", "org/springframework/session/jdbc/schema-drop-mysql.sql",
                "jdbc:oracle:", "org/springframework/session/jdbc/schema-drop-oracle.sql",
                "jdbc:postgresql:", "org/springframework/session/jdbc/schema-drop-postgresql.sql",
                "jdbc:sqlite:", "org/springframework/session/jdbc/schema-drop-sqlite.sql",
                "jdbc:sqlserver:", "org/springframework/session/jdbc/schema-drop-sqlserver.sql",
                "jdbc:sybase:", "org/springframework/session/jdbc/schema-drop-sybase.sql"
        );
        private final Map<String, String> initSchemaStructure = Map.of(
                "jdbc:postgresql:", "CREATE SCHEMA IF NOT EXISTS %s"
        );


        private String jdbcUrl;
        private String username;
        private String password;
        private String schemaPattern = "[a-z0-9_]+";
        private String database = "dop";
        private String schemaDefault = "dop";

        public String generateUrl(String schema) {
            return jdbcUrl.replaceAll("/[^/]+$", String.format("/%s?currentSchema=%s", database, schema));
        }

        public String locatorCreateTableStructure() {
            for (Map.Entry<String, String> entry : createTableStructure.entrySet()) {
                if (jdbcUrl.startsWith(entry.getKey())) {
                    return entry.getValue();
                }
            }
            throw new DopException("Database not supported.");
        }
        public String scriptInitSchemaStructure() {
            for (Map.Entry<String, String> entry : initSchemaStructure.entrySet()) {
                if (jdbcUrl.startsWith(entry.getKey())) {
                    return entry.getValue();
                }
            }
            throw new DopException("Database not supported.");
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
