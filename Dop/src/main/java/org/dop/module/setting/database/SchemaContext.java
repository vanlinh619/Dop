package org.dop.module.setting.database;

public class SchemaContext {
    private static final ThreadLocal<String> schemaContext = new ThreadLocal<>();

    public static void setSchema(String schema) {
        schemaContext.set(schema);
    }

    public static String getSchema() {
        return schemaContext.get();
    }

    public static void clear() {
        schemaContext.remove();
    }
}
