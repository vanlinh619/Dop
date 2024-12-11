package org.dop.entity.state;

import lombok.experimental.FieldNameConstants;

@FieldNameConstants(onlyExplicitlyIncluded = true)
public enum StartupName {
    @FieldNameConstants.Include REGISTERED_CLIENT,
    @FieldNameConstants.Include LANGUAGE_SUPPORT,
}
