package org.dop.module.exception.advice;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.dop.module.exception.DopException;
import org.dop.module.exception.pojo.ErrorCode;
import org.dop.module.exception.pojo.ErrorResponse;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.security.authorization.AuthorizationDeniedException;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@RestControllerAdvice(annotations = RestController.class)
@Order(Ordered.HIGHEST_PRECEDENCE)
@RequiredArgsConstructor
public class DopRestControllerExceptionAdvice {

    private final HttpServletRequest request;

    /**
     * Exception for valid field
     * */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({MethodArgumentNotValidException.class, BindException.class})
    public ErrorResponse handleValidationExceptions(BindException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach(error -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return getErrorResponseBuilder()
                .code(ErrorCode.ARGUMENT_INVALID.name())
                .detail(errors.toString())
                .build();
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({DopException.class})
    public ErrorResponse handleValidationExceptions(DopException ex) {
        return getErrorResponseBuilder()
                .code(ex.getCode())
                .detail(ex.getDetail())
                .build();
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler({Exception.class})
    public ErrorResponse handleValidationExceptions(Exception ex) {
        return getErrorResponseBuilder()
                .code(ErrorCode.UNKNOWN.name())
                .build();
    }

    @ResponseStatus(HttpStatus.FORBIDDEN)
    @ExceptionHandler({AuthorizationDeniedException.class})
    public ErrorResponse handleValidationExceptions(AuthorizationDeniedException ex) {
        return getErrorResponseBuilder()
                .code(ErrorCode.FORBIDDEN.name())
                .build();
    }

    private ErrorResponse.ErrorResponseBuilder getErrorResponseBuilder() {
        return ErrorResponse.builder()
                .trace(UUID.randomUUID().toString())
                .timestamp(Instant.now())
                .path(request.getRequestURI());
    }
}
