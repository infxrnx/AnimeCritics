package com.project.anime.aop.aspect;

import com.project.anime.service.RequestStatsService;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect
@Component
class RequestStatsAspect {
  RequestStatsService requestStatsService;

  public RequestStatsAspect(RequestStatsService requestStatsService) {
    this.requestStatsService = requestStatsService;
  }

  @Around(
      "@within(com.project.anime.aop.annotation.RequestStats) ||"
          + " @annotation(com.project.anime.aop.annotation.RequestStats)")
  public Object incrementRequestStats(ProceedingJoinPoint joinPoint) throws Throwable {
    requestStatsService.increment();
    return joinPoint.proceed();
  }
}