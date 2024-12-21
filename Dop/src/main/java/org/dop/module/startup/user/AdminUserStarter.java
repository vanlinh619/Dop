package org.dop.module.startup.user;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.dop.config.property.UserAdminProperties;
import org.dop.entity.*;
import org.dop.entity.embeded.EmailEmbedded;
import org.dop.entity.state.Provider;
import org.dop.entity.state.StartupName;
import org.dop.entity.state.UserPrimaryStatus;
import org.dop.module.startup.Starter;
import org.dop.repository.*;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Locale;

@Service(StartupName.ADMIN_USER)
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
@RequiredArgsConstructor
public class AdminUserStarter implements Starter {

    private final UserAdminProperties userAdminProperties;

    private final UserPrimaryRepository userPrimaryRepository;
    private final UserProviderRepository userProviderRepository;
    private final UserProfileRepository userProfileRepository;
    private final EntityManager entityManager;

    private final PasswordEncoder passwordEncoder;

    private final @Qualifier(StartupName.LANGUAGE_SUPPORT) Starter languageSupportStarter;

    @Transactional
    @Override
    public void start() {
        /// Create user
        UserPrimary userPrimary = UserPrimary.builder()
                .username(userAdminProperties.getUsername())
                .password(passwordEncoder.encode(userAdminProperties.getPassword()))
                .email(new EmailEmbedded(userAdminProperties.getEmail(), true))
                .status(UserPrimaryStatus.ENABLED)
                .build();
        userPrimaryRepository.save(userPrimary);

        /// Add provider
        UserProvider userProvider = UserProvider.builder()
                .id(userPrimary.getId())
                .provider(Provider.LOCAL)
                .providerId(userPrimary.getId().toString())
                .build();
        userProviderRepository.save(userProvider);

        /// Add profile
        Language language = entityManager.find(Language.class, userAdminProperties.getLanguageCode());
        UserProfile userProfile = UserProfile.builder()
                .id(userPrimary.getId())
                .givenName(userAdminProperties.getName())
                .fullName(userAdminProperties.getName())
                .language(language)
                .locale(Locale.of(userAdminProperties.getLanguageCode().name()))
                .build();
        userProfileRepository.save(userProfile);
    }

    @Override
    public int priority() {
        return languageSupportStarter.priority() + 1;
    }
}
