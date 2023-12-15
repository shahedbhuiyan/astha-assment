package com.booking.svc.common.logggers.aspect;

import com.booking.svc.common.logggers.CommonTraceLoggerAspect;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class BookingServiceTracingAspect extends CommonTraceLoggerAspect {

    @Pointcut("execution(* com.booking.svc.resources..*(..)))")
    public void skeletonControllerAspect() {}

    @Pointcut("execution(* com.booking.svc.services..*.*(..)))")
    public void skeletonServiceTrace() {}

    @Around("skeletonServiceTrace() && !noLogging()")
    public Object logService(ProceedingJoinPoint joinPoint) throws Throwable {
        return trace(joinPoint);
    }

    @Around("skeletonControllerAspect()")
    public Object logController(ProceedingJoinPoint joinPoint) throws Throwable {
        return trace(joinPoint);
    }

}
