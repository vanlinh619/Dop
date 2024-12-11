package org.dop.module.startup.language;

import lombok.RequiredArgsConstructor;
import org.dop.entity.Language;
import org.dop.entity.state.StartupName;
import org.dop.module.startup.Starter;
import org.dop.repository.LanguageRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service(StartupName.Fields.LANGUAGE_SUPPORT)
@RequiredArgsConstructor
public class LanguageSupportStarter implements Starter {

    private final LanguageRepository languageRepository;

    @Override
    public void start() {
        List<Language> languages = List.of(
                new Language("vi", "Việt Nam"),
                new Language("en", "English")
        );
        languageRepository.saveAll(languages);
    }
}
