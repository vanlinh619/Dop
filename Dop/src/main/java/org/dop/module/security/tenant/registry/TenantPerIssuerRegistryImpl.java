package org.dop.module.security.tenant.registry;

import org.dop.module.security.authorizationserver.pojo.error.AuthorizationServerError;
import org.dop.module.security.authorizationserver.pojo.exception.ClassNotFoundException;
import org.dop.module.security.authorizationserver.pojo.exception.NoIssuerFoundException;
import org.springframework.security.oauth2.server.authorization.context.AuthorizationServerContext;
import org.springframework.security.oauth2.server.authorization.context.AuthorizationServerContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

@Service
public class TenantPerIssuerRegistryImpl implements TenantPerIssuerRegistry {
    private final ConcurrentMap<String, Map<Class<?>, Object>> registry = new ConcurrentHashMap<>();

    @Override
    public <T> void register(String tenantId, Class<T> componentClass, T component) {
        Assert.hasText(tenantId, "tenantId cannot be empty");
        Assert.notNull(componentClass, "componentClass cannot be null");
        Assert.notNull(component, "component cannot be null");
        Map<Class<?>, Object> components = this.registry.computeIfAbsent(tenantId, (key) -> new ConcurrentHashMap<>());
        components.put(componentClass, component);
    }

    @Override
    public <T> T get(Class<T> componentClass) {
        AuthorizationServerContext context = AuthorizationServerContextHolder.getContext();
        if (context == null || context.getIssuer() == null) {
            throw new NoIssuerFoundException(
                    AuthorizationServerError.ISSUER_NOT_FOUND.name(),
                    "Not found issuer."
            );
        }
        for (Map.Entry<String, Map<Class<?>, Object>> entry : this.registry.entrySet()) {
            if (context.getIssuer().endsWith(entry.getKey())) {
                return componentClass.cast(entry.getValue().get(componentClass));
            }
        }
        throw new ClassNotFoundException(
                AuthorizationServerError.CLASS_NOT_FOUND.name(),
                String.format("Class %s not found", componentClass.getName())
        );
    }
}
