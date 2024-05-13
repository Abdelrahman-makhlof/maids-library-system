package com.maids.library.management.system.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.LocalDateTime;

@Component
public class LoggingAspect {

    private final static Logger log = LoggerFactory.getLogger(LoggingAspect.class);

    @Before("execution(* com.maids.library.management.system.service.*.*(..))")
    public void logMethodCall(JoinPoint joinPoint) {
        log.info("Executing method: {} ", joinPoint.getSignature().getName());
    }

    @AfterThrowing(pointcut = "execution(* com.maids.library.management.system.service.*.*(..))", throwing = "ex")
    public void logException(JoinPoint joinPoint, Throwable ex) {
        log.error("Exception in method: {} ", joinPoint.getSignature().getName(), ex);
    }

    @Around("execution(* com.maids.library.management.system.service.*.*(..))")
    public Object logPerformance(ProceedingJoinPoint joinPoint) throws Throwable {
        var startTime = LocalDateTime.now();
        var result = joinPoint.proceed();
        var executionTime = Duration.between(LocalDateTime.now(), startTime);
        log.info("Execution time of {} : {} ms", joinPoint.getSignature().getName(), executionTime);

        return result;
    }

}
