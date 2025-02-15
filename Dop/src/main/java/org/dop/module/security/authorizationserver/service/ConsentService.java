package org.dop.module.security.authorizationserver.service;

import org.dop.module.security.authorizationserver.pojo.projection.ScopeDescriptionProjection;

import java.util.Locale;
import java.util.Set;

public interface ConsentService {
    Set<ScopeDescriptionProjection> withDescription(Set<String> scopes, Locale locale);
}
