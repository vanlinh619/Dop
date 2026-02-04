package org.dop.module.startup.user;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.dop.config.property.RoleDefaultProperties;
import org.dop.config.property.UserAdminProperties;
import org.dop.entity.*;
import org.dop.entity.embeded.AddressEmbedded;
import org.dop.entity.embeded.EmailEmbedded;
import org.dop.entity.embeded.PhoneEmbedded;
import org.dop.entity.state.Gender;
import org.dop.entity.state.IdentifierMethod;
import org.dop.entity.state.Provider;
import org.dop.entity.state.UserPrimaryStatus;
import org.dop.module.startup.Starter;
import org.dop.module.startup.StartupName;
import org.dop.repository.*;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.time.ZoneId;
import java.util.List;
import java.util.Locale;
import java.util.Set;

@Service(StartupName.ADMIN_USER)
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
@RequiredArgsConstructor
public class AdminUserStarter implements Starter {

    private final UserAdminProperties userAdminProperties;
    private final RoleDefaultProperties roleDefaultProperties;

    private final UserPrimaryRepository userPrimaryRepository;
    private final UserProviderRepository userProviderRepository;
    private final UserProfileRepository userProfileRepository;
    private final UserIdentifierMethodRepository userIdentifierMethodRepository;
    private final RoleRepository roleRepository;
    private final EntityManager entityManager;

    private final PasswordEncoder passwordEncoder;

    private final @Qualifier(StartupName.LANGUAGE_SUPPORT) Starter languageSupportStarter;
    private final @Qualifier(StartupName.ROLE_DEFAULT) Starter roleDefaultStarter;

    @Transactional
    @Override
    public void start() {
        // Create user
        Role role = roleRepository.findById(roleDefaultProperties.getRoleSuper())
                .orElseThrow(() -> new EntityNotFoundException(String.format("Role %s not found", roleDefaultProperties.getRoleSuper())));
        UserPrimary userPrimary = UserPrimary.builder()
                .username(userAdminProperties.getUsername())
                .password(passwordEncoder.encode(userAdminProperties.getPassword()))
                .email(new EmailEmbedded(userAdminProperties.getEmail(), true))
                .status(UserPrimaryStatus.ENABLED)
                .roles(Set.of(role))
                .build();
        userPrimaryRepository.save(userPrimary);

        // Add provider
        UserProvider userProvider = UserProvider.builder()
                .id(userPrimary.getId())
                .provider(Provider.LOCAL)
                .providerId(userPrimary.getId().toString())
                .build();
        userProviderRepository.save(userProvider);

        // Add login methods
        List<UserIdentifierMethod> loginMethods = List.of(
                UserIdentifierMethod.builder()
                        .user(userPrimary)
                        .identifierMethod(IdentifierMethod.USERNAME)
                        .build(),
                UserIdentifierMethod.builder()
                        .user(userPrimary)
                        .identifierMethod(IdentifierMethod.EMAIL)
                        .build()
        );
        userIdentifierMethodRepository.saveAll(loginMethods);

        // Add profile
        Language language = entityManager.find(Language.class, userAdminProperties.getLanguageCode());
        AddressEmbedded address = AddressEmbedded.builder()
                .addressLine1("Admin Address Line 1")
                .city("Hồ Chí Minh")
                .country("Việt Nam")
                .build();
        PhoneEmbedded phone = PhoneEmbedded.builder()
                .number("0000000000")
                .verified(false)
                .build();
        UserProfile userProfile = UserProfile.builder()
                .id(userPrimary.getId())
                .givenName(userAdminProperties.getName())
                .fullName(userAdminProperties.getName())
                .language(language)
                .locale(Locale.of(userAdminProperties.getLanguageCode().name()))
                .birthDate(Instant.now())
                .profile("https://github.com/vanlinh619")
                .website("https://github.com/vanlinh619")
                .zoneInfo(ZoneId.systemDefault())
                .gender(Gender.MALE)
                .address(address)
                .phone(phone)
                .picture("https://avatars.githubusercontent.com/u/71812422?v=4")
                .build();
        userProfileRepository.save(userProfile);
    }

    @Override
    public int priority() {
        return languageSupportStarter.priority() + roleDefaultStarter.priority() + 1;
    }
}
