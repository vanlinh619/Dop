package org.dop.module.security.authorizationserver.pojo.exception;

import org.dop.module.exception.DopException;

public class ClassNotFoundException extends DopException {

    public ClassNotFoundException(String code, String message) {
        super(code, message, null, null);
    }
}
