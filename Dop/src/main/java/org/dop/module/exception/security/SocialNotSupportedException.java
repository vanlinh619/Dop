package org.dop.module.exception.security;

import org.dop.module.exception.DopException;

public class SocialNotSupportedException extends DopException {

    public SocialNotSupportedException(String message) {
        super(message);
    }
}
