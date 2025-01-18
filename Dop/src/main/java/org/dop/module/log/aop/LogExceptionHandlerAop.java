package org.dop.module.log.aop;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.dop.module.helper.FormatHelper;
import org.springframework.stereotype.Service;

/**
 * Log exception advice.
 */
@Aspect
@Log4j2
@Service
@RequiredArgsConstructor
public class LogExceptionHandlerAop {

    private final FormatHelper formatHelper;

    @Pointcut("""
            execution(* org.dop.module.global.advice.DopExceptionAdvice.*(..))
            && @annotation(org.springframework.web.bind.annotation.ExceptionHandler)
            """
    )
    public void logErrorResponse() {
    }

    @AfterReturning(pointcut = "logErrorResponse()", returning = "result")
    public void logAfter(JoinPoint joinPoint, Object result) {
        Object[] args = joinPoint.getArgs();
        for (Object arg : args) {
            if (arg instanceof Exception e) {
                log.error("[Dop - Exception Advice] Return error.{}\n - message: {}", formatHelper.formatProperties(result), e.getMessage());
            }
        }
    }
}
