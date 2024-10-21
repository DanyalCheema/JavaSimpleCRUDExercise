package com.application.crud.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * Author: Danyal Cheema Date:10/21/2024
 */

@Aspect
@Component
public class LoggingAspect {
  private final Logger logger = LoggerFactory.getLogger(this.getClass());

  @Before("execution(* com.application.crud.controller.*.*(..))")
  public void logBefore(JoinPoint joinPoint) {
    logger.info("Executing: {} ", joinPoint.getSignature().toShortString());
  }

  @AfterReturning(pointcut = "execution(* com.application.crud.controller.*.*(..))", returning = "result")
  public void logAfterReturning(JoinPoint joinPoint, Object result) {
    logger.info("Executed: " + joinPoint.getSignature().toShortString() + " with result: " + result);
  }
}
