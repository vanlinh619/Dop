package org.dop.module.setting.exception;

import org.dop.module.exception.DopException;

public class SchemaNameException extends DopException {

    public SchemaNameException(String code, String message) {
        super(code, message, null, null);
    }
}
