package org.dop.module.global.advice;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.dop.module.global.pojo.ErrorCode;
import org.dop.module.global.pojo.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@RestControllerAdvice
@RequiredArgsConstructor
public class DopExceptionAdvice {

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


    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler({Exception.class})
    public ErrorResponse handleValidationExceptions(Exception ex) {
        return getErrorResponseBuilder()
                .code(ErrorCode.UNKNOWN.name())
                .build();
    }

    private ErrorResponse.ErrorResponseBuilder getErrorResponseBuilder() {
        return ErrorResponse.builder()
                .trace(UUID.randomUUID().toString())
                .timestamp(Instant.now())
                .path(request.getRequestURI());
    }
}
