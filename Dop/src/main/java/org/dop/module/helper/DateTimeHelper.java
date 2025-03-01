package org.dop.module.helper;

import jakarta.annotation.Nullable;

import java.time.Instant;

public interface DateTimeHelper {

    @Nullable
    Instant parse(String date);
}
