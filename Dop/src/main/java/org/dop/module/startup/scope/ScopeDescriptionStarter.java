package org.dop.module.startup.scope;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.dop.config.property.ClientMasterProperties;
import org.dop.entity.Language;
import org.dop.entity.Oauth2RegisteredClient;
import org.dop.entity.ScopeDescription;
import org.dop.entity.state.LanguageCode;
import org.dop.module.startup.Starter;
import org.dop.module.startup.StartupName;
import org.dop.module.startup.language.LanguageSupportStarter;
import org.dop.module.startup.registeredclient.Oauth2RegisteredClientStarter;
import org.dop.repository.ScopeDescriptionRepository;
import org.dop.repository.ScopeRepository;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Scope;
import org.springframework.security.oauth2.core.oidc.OidcScopes;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Locale;

@Service(StartupName.SCOPE_DESCRIPTION)
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
@RequiredArgsConstructor
public class ScopeDescriptionStarter implements Starter {

    private final ClientMasterProperties clientMasterProperties;
    private final ScopeRepository scopeRepository;
    private final ScopeDescriptionRepository scopeDescriptionRepository;
    private final EntityManager entityManager;
    private final MessageSource messageSource;

    private final LanguageSupportStarter languageSupportStarter;
    private final Oauth2RegisteredClientStarter oauth2RegisteredClientStarter;

    @Transactional
    @Override
    public void start() {
        String clientMasterId = oauth2RegisteredClientStarter.getIdRegisteredClientMaster();
        // Add Scope Master
        String masterViDescription = messageSource.getMessage("scope.description.master", null, Locale.of(LanguageCode.vi.name()));
        String masterEnDescription = messageSource.getMessage("scope.description.master", null, Locale.of(LanguageCode.en.name()));
        org.dop.entity.Scope scopeMaster = org.dop.entity.Scope.builder()
                .id(clientMasterProperties.getScopeMaster())
                .name(clientMasterProperties.getScopeMasterName())
                .client(entityManager.getReference(Oauth2RegisteredClient.class, clientMasterId))
                .description(masterViDescription)
                .build();
        scopeRepository.save(scopeMaster);

        // Add Scope OpenId
        String openIdViDescription = messageSource.getMessage("scope.description.open-id", null, Locale.of(LanguageCode.vi.name()));
        String openIdEnDescription = messageSource.getMessage("scope.description.open-id", null, Locale.of(LanguageCode.en.name()));
        org.dop.entity.Scope scopeOpenId = org.dop.entity.Scope.builder()
                .id(OidcScopes.OPENID)
                .name("Open Id")
                .description(openIdViDescription)
                .build();
        scopeRepository.save(scopeOpenId);

        // Add Scope Profile
        String profileViDescription = messageSource.getMessage("scope.description.profile", null, Locale.of(LanguageCode.vi.name()));
        String profileEnDescription = messageSource.getMessage("scope.description.profile", null, Locale.of(LanguageCode.en.name()));
        org.dop.entity.Scope scopeProfile = org.dop.entity.Scope.builder()
                .id(OidcScopes.PROFILE)
                .name("Profile")
                .description(profileViDescription)
                .build();
        scopeRepository.save(scopeProfile);

        // Add Scope Email
        String emailViDescription = messageSource.getMessage("scope.description.email", null, Locale.of(LanguageCode.vi.name()));
        String emailEnDescription = messageSource.getMessage("scope.description.email", null, Locale.of(LanguageCode.en.name()));
        org.dop.entity.Scope scopeEmail = org.dop.entity.Scope.builder()
                .id(OidcScopes.EMAIL)
                .name("Email")
                .description(emailViDescription)
                .build();
        scopeRepository.save(scopeEmail);

        // Add Scope Address
        String addressViDescription = messageSource.getMessage("scope.description.address", null, Locale.of(LanguageCode.vi.name()));
        String addressEnDescription = messageSource.getMessage("scope.description.address", null, Locale.of(LanguageCode.en.name()));
        org.dop.entity.Scope scopeAddress = org.dop.entity.Scope.builder()
                .id(OidcScopes.ADDRESS)
                .name("Address")
                .description(addressViDescription)
                .build();
        scopeRepository.save(scopeAddress);

        // Add Scope Phone
        String phoneViDescription = messageSource.getMessage("scope.description.phone", null, Locale.of(LanguageCode.vi.name()));
        String phoneEnDescription = messageSource.getMessage("scope.description.phone", null, Locale.of(LanguageCode.en.name()));
        org.dop.entity.Scope scopePhone = org.dop.entity.Scope.builder()
                .id(OidcScopes.PHONE)
                .name("Phone")
                .description(phoneViDescription)
                .build();
        scopeRepository.save(scopePhone);

        // Add Scope Description
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
                        .build(),
                ScopeDescription.builder()
                        .scope(scopeEmail)
                        .language(entityManager.getReference(Language.class, LanguageCode.vi))
                        .description(emailViDescription)
                        .build(),
                ScopeDescription.builder()
                        .scope(scopeEmail)
                        .language(entityManager.getReference(Language.class, LanguageCode.en))
                        .description(emailEnDescription)
                        .build(),
                ScopeDescription.builder()
                        .scope(scopeAddress)
                        .language(entityManager.getReference(Language.class, LanguageCode.vi))
                        .description(addressViDescription)
                        .build(),
                ScopeDescription.builder()
                        .scope(scopeAddress)
                        .language(entityManager.getReference(Language.class, LanguageCode.en))
                        .description(addressEnDescription)
                        .build(),
                ScopeDescription.builder()
                        .scope(scopePhone)
                        .language(entityManager.getReference(Language.class, LanguageCode.vi))
                        .description(phoneViDescription)
                        .build(),
                ScopeDescription.builder()
                        .scope(scopePhone)
                        .language(entityManager.getReference(Language.class, LanguageCode.en))
                        .description(phoneEnDescription)
                        .build()
        );
        scopeDescriptionRepository.saveAll(descriptions);
    }

    @Override
    public int priority() {
        return languageSupportStarter.priority() + oauth2RegisteredClientStarter.priority() + 1;
    }
}
