package org.dop.module.language.service;

import jakarta.annotation.Nullable;
import org.dop.entity.state.LanguageCode;

public interface LanguageService {

    LanguageCode getLanguageCodeDefault(@Nullable String code);
}
