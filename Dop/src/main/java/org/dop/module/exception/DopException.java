package org.dop.module.exception;

import lombok.Getter;

@Getter
public class DopException extends RuntimeException {

    private String code;
    private Object detail;

    public DopException(String message) {
        super(message);
    }

    public DopException(String code, String message, Object detail, Throwable cause) {
        super(message, cause);
        this.code = code;
        this.detail = detail;
    }
}
