package com.booking.svc.common.logggers.aspect;

import com.booking.svc.common.logggers.CommonPerformanceLoggerAspect;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class BookingServicePerformanceAspect extends CommonPerformanceLoggerAspect {

    @Pointcut("execution(public * com.booking.svc.repositories..*.*(..))")
    public void repositoryPerformanceTrace() {
    }

    @Around("repositoryPerformanceTrace())")
    public Object calculateServicePerformance(ProceedingJoinPoint joinPoint) throws Throwable {
        return tracePerformance(joinPoint);
    }

}
