package org.dop.module.security.service;

import lombok.RequiredArgsConstructor;
import org.dop.entity.state.LanguageCode;
import org.dop.module.security.pojo.projection.ScopeDescriptionProjection;
import org.dop.repository.ScopeDescriptionRepository;
import org.springframework.stereotype.Service;

import java.util.Locale;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class ConsentServiceImpl implements ConsentService {

    private final ScopeDescriptionRepository scopeDescriptionRepository;


    @Override
    public Set<ScopeDescriptionProjection> withDescription(Set<String> scopes, Locale locale) {
        LanguageCode languageCode = LanguageCode.valueOf(locale.getLanguage());
        return scopeDescriptionRepository.listDescriptionByScope(scopes, languageCode);
    }
}
