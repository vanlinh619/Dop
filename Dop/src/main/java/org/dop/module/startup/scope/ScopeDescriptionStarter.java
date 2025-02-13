package org.dop.module.startup.scope;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.dop.config.property.ClientMasterProperties;
import org.dop.entity.Language;
import org.dop.entity.ScopeDescription;
import org.dop.entity.state.LanguageCode;
import org.dop.entity.state.StartupName;
import org.dop.module.startup.Starter;
import org.dop.module.startup.language.LanguageSupportStarter;
import org.dop.repository.ScopeRepository;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Scope;
import org.springframework.security.oauth2.core.oidc.OidcScopes;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Locale;

@Service(StartupName.SCOPE_DESCRIPTION)
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
@RequiredArgsConstructor
public class ScopeDescriptionStarter implements Starter {

    private final LanguageSupportStarter languageSupportStarter;

    private final ClientMasterProperties clientMasterProperties;
    private final ScopeRepository scopeRepository;
    private final EntityManager entityManager;
    private final MessageSource messageSource;


    @Override
    public void start() {
        /// Add Scope
        String masterViDescription = messageSource.getMessage("scope.description.master", null, Locale.of(LanguageCode.vi.name()));
        String masterEnDescription = messageSource.getMessage("scope.description.master", null, Locale.of(LanguageCode.en.name()));
        org.dop.entity.Scope scopeMaster = org.dop.entity.Scope.builder()
                .id(clientMasterProperties.getScopeMaster())
                .description(masterViDescription)
                .build();
        scopeRepository.save(scopeMaster);

        String openIdViDescription = messageSource.getMessage("scope.description.open-id", null, Locale.of(LanguageCode.vi.name()));
        String openIdEnDescription = messageSource.getMessage("scope.description.open-id", null, Locale.of(LanguageCode.en.name()));
        org.dop.entity.Scope scopeOpenId = org.dop.entity.Scope.builder()
                .id(OidcScopes.OPENID)
                .description(openIdViDescription)
                .build();
        scopeRepository.save(scopeOpenId);

        String profileViDescription = messageSource.getMessage("scope.description.profile", null, Locale.of(LanguageCode.vi.name()));
        String profileEnDescription = messageSource.getMessage("scope.description.profile", null, Locale.of(LanguageCode.en.name()));
        org.dop.entity.Scope scopeProfile = org.dop.entity.Scope.builder()
                .id(OidcScopes.PROFILE)
                .description(profileViDescription)
                .build();
        scopeRepository.save(scopeProfile);

        /// Add Scope Description
        List<ScopeDescription> descriptions = List.of(
                ScopeDescription.builder()
                        .scope(scopeMaster)
                        .language(entityManager.getReference(Language.class, LanguageCode.vi))
                        .description(masterViDescription)
                        .build(),
                ScopeDescription.builder()
                        .scope(scopeMaster)
                        .language(entityManager.getReference(Language.class, LanguageCode.en))
                        .description(masterEnDescription)
                        .build(),
                ScopeDescription.builder()
                        .scope(scopeOpenId)
                        .language(entityManager.getReference(Language.class, LanguageCode.vi))
                        .description(openIdViDescription)
                        .build(),
                ScopeDescription.builder()
                        .scope(scopeOpenId)
                        .language(entityManager.getReference(Language.class, LanguageCode.en))
                        .description(openIdEnDescription)
                        .build(),
                ScopeDescription.builder()
                        .scope(scopeProfile)
                        .language(entityManager.getReference(Language.class, LanguageCode.vi))
                        .description(profileViDescription)
                        .build(),
                ScopeDescription.builder()
                        .scope(scopeProfile)
                        .language(entityManager.getReference(Language.class, LanguageCode.en))
                        .description(profileEnDescription)
                        .build()
                /// Todo: Add more scope description
        );

    }

    @Override
    public int priority() {
        return languageSupportStarter.priority() + 1;
    }
}
