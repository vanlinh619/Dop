package org.dop.module.tenant.context;

public class TenantContext {
    private static final ThreadLocal<String> tenantContext = new ThreadLocal<>();

    public static void setCurrent(String schema) {
        tenantContext.set(schema);
    }

    public static String getTenant() {
        String schema = tenantContext.get();
        return schema != null ? schema : "" ;
    }

    public static void clear() {
        tenantContext.remove();
    }
}
