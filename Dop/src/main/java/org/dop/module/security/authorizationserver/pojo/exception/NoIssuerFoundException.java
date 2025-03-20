package org.dop.module.security.authorizationserver.pojo.exception;

import org.dop.module.exception.DopException;


public class NoIssuerFoundException extends DopException {

    public NoIssuerFoundException(String message) {
        super(message);
    }

    public NoIssuerFoundException(String code, String message) {
        super(code, message, null, null);
    }
}
