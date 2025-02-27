package org.dop.entity.state;

import jakarta.annotation.Nullable;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Provider {
    LOCAL("local"),
    GOOGLE("google");

    /// Is registration id
    private final String provider;

    @Nullable
    public static Provider parse(String provider) {
        for (Provider p : values()) {
            if (p.provider.equals(provider)) {
                return p;
            }
        }
        return null;
    }
}
