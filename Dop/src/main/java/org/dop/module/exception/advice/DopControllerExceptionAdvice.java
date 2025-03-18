package org.dop.module.exception.advice;

import lombok.RequiredArgsConstructor;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ControllerAdvice;

@ControllerAdvice(annotations = Controller.class)
@Order
@RequiredArgsConstructor
public class DopControllerExceptionAdvice {

//    @ExceptionHandler({Exception.class})
//    public String handleValidationExceptions(Exception ex) {
//        return "redicerror";
//    }
}
