package org.dop.module.exception;

public class InternalErrorException extends DopException {
    public InternalErrorException(Enum<?> code, String message) {
        super(code, message, null, null);
    }
}
