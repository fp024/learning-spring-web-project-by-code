package org.fp024.aop;

import java.util.Arrays;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Aspect
@Slf4j
@Component
public class LogAdvice {

  /*
   argNames 값에대한 설명:
     디버그 정보 없이 컴파일하거나 런타임에 포인트컷을 해석할 때 어드바이스 선언에 사용된 인수의 이름을 사용할 수 없습니다.
     이러한 상황에서만 주석에 arg 이름을 제공해야 합니다.
     이러한 이름은 주석이 달린 메서드에 사용된 이름을 복제해야 합니다(MUST).
     형식은 쉼표로 구분된 간단한 목록입니다.
  */
  @Before(
      value = "execution(* org.fp024.service.SampleService*.*(..)) && args(str1, str2)",
      argNames = "str1,str2")
  public void logBefore(String str1, String str2) {
    LOGGER.info("================================");
    LOGGER.info("str1: {}", str1);
    LOGGER.info("str2: {}", str2);
  }

  @AfterThrowing(
      pointcut = "execution(* org.fp024.service.SampleService*.*(..))",
      throwing = "exception")
  void logException(Exception exception) {
    LOGGER.info("Exception... !!!!");
    LOGGER.info("exception: {}", exception.getMessage(), exception);
  }

  @Around("execution(* org.fp024.service.SampleService*.*(..))")
  public Object logTime(ProceedingJoinPoint proceedingJoinPoint) {
    long start = System.currentTimeMillis();

    LOGGER.info("Target: {}", proceedingJoinPoint.getTarget());
    LOGGER.info("Param: {}", Arrays.toString(proceedingJoinPoint.getArgs()));

    // invoke method
    Object result = null;

    try {
      result = proceedingJoinPoint.proceed();
    } catch (Throwable e) {
      LOGGER.error("{}", e.getMessage(), e);
    }

    long end = System.currentTimeMillis();

    LOGGER.info("TIME: {}", end - start);

    return result;
  }
}
