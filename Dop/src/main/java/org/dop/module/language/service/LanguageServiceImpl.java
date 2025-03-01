package org.dop.module.language.service;

import jakarta.annotation.Nullable;
import org.dop.entity.state.LanguageCode;
import org.springframework.stereotype.Service;

import java.util.Arrays;

@Service
public class LanguageServiceImpl implements LanguageService {

    @Override
    public LanguageCode getLanguageCodeDefault(@Nullable String code) {
        if (code == null) {
            return LanguageCode.vi;
        }
        return Arrays.stream(LanguageCode.values())
                .filter(languageCode -> languageCode.name().equalsIgnoreCase(code))
                .findFirst()
                .orElse(LanguageCode.vi);
    }
}
