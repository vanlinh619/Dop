package org.dop.module.exception;

import lombok.Getter;

import java.util.UUID;

@Getter
public class DopException extends RuntimeException {

    private final Enum<?> code;
    private final Object detail;

    private final String trace;

    public DopException(Enum<?> code, String message) {
        super(message);
        this.code = code;
        this.detail = null;
        this.trace = UUID.randomUUID().toString();
    }

    public DopException(Enum<?> code, String message, Object detail, Throwable cause) {
        super(message, cause);
        this.code = code;
        this.detail = detail;
        this.trace = cause instanceof DopException dopException ? dopException.getTrace() : UUID.randomUUID().toString();
    }
}
