package org.dop.module.setting.database;

public class TenantContext {
    private static final ThreadLocal<String> schemaContext = new ThreadLocal<>();

    public static void setCurrent(String schema) {
        schemaContext.set(schema);
    }

    public static String getTenant() {
        return schemaContext.get();
    }

    public static void clear() {
        schemaContext.remove();
    }
}
