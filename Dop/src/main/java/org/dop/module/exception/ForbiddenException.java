package org.dop.module.exception;

public class ForbiddenException extends DopException{
    public ForbiddenException(String message) {
        super(message);
    }

    public ForbiddenException(String code, String message) {
        super(code, message, null, null);
    }
}
