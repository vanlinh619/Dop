package org.dop.module.security.authorizationserver.service;

public interface TenantPerIssuerComponentRegistry {
    <T> void register(String tenantId, Class<T> componentClass, T component);

    <T> T get(Class<T> componentClass);
}
