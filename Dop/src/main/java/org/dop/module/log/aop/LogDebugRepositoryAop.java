package org.dop.module.log.aop;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Service;

/**
 * This class supports for log debug repository. <p>
 * <b>Level: DEBUG</b>
 */
@Aspect
@Log4j2
@Service
@RequiredArgsConstructor
public class LogDebugRepositoryAop {

    @Pointcut("execution(* org.dop.repository.*.*(..))")
    public void logPointcut() {
    }

    @Before("logPointcut()")
    public void logBefore(JoinPoint joinPoint) {
        log.debug("[Dop - Execute query] signature {}", joinPoint.getSignature().toString());
    }
}
