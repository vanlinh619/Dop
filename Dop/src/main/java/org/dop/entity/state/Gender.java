package org.dop.entity.state;

import java.util.Arrays;

public enum Gender {
    MALE,
    FEMALE;

    public static Gender parse(String gender) {
        if (Arrays.stream(values()).anyMatch(g -> g.name().equals(gender))) {
            return Gender.valueOf(gender);
        }
        return null;
    }
}
