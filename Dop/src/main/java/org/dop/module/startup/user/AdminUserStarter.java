package org.dop.module.startup.user;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.dop.config.property.UserAdminProperties;
import org.dop.entity.Language;
import org.dop.entity.UserPrimary;
import org.dop.entity.UserProfile;
import org.dop.entity.UserProvider;
import org.dop.entity.embeded.EmailEmbedded;
import org.dop.entity.state.Provider;
import org.dop.entity.state.StartupName;
import org.dop.entity.state.UserPrimaryRole;
import org.dop.entity.state.UserPrimaryStatus;
import org.dop.module.startup.Starter;
import org.dop.repository.UserPrimaryRepository;
import org.dop.repository.UserProfileRepository;
import org.dop.repository.UserProviderRepository;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Locale;

@Service(StartupName.Fields.ADMIN_USER)
@RequiredArgsConstructor
public class AdminUserStarter implements Starter {

    private final UserAdminProperties userAdminProperties;
    private final UserPrimaryRepository userPrimaryRepository;
    private final UserProviderRepository userProviderRepository;
    private final UserProfileRepository userProfileRepository;
    private final EntityManager entityManager;
    private final PasswordEncoder passwordEncoder;
    private final @Qualifier(StartupName.Fields.LANGUAGE_SUPPORT) Starter languageSupportStarter;

    @Transactional
    @Override
    public void start() {
        UserPrimary userPrimary = UserPrimary.builder()
                .username(userAdminProperties.getUsername())
                .password(passwordEncoder.encode(userAdminProperties.getPassword()))
                .email(new EmailEmbedded(userAdminProperties.getEmail(), true))
                .role(UserPrimaryRole.ADMIN)
                .status(UserPrimaryStatus.ACTIVE)
                .build();
        userPrimaryRepository.save(userPrimary);

        UserProvider userProvider = UserProvider.builder()
                .id(userPrimary.getId())
                .provider(Provider.LOCAL)
                .providerId(userPrimary.getId().toString())
                .build();
        userProviderRepository.save(userProvider);

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
