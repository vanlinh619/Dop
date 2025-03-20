package org.dop.module.exception;

public class BadRequestException extends DopException {

    public BadRequestException(String code, String message) {
        super(code, message, null, null);
    }

}
