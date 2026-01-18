package org.dop.module.security.service;

import org.dop.module.security.pojo.projection.ScopeDescriptionProjection;

import java.util.Locale;
import java.util.Set;

public interface ConsentService {
    Set<ScopeDescriptionProjection> withDescription(Set<String> scopes, Locale locale);
}
