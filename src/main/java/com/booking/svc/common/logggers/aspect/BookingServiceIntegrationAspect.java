package com.booking.svc.common.logggers.aspect;


import com.booking.svc.common.logggers.CommonIntegrationLoggerAspect;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class BookingServiceIntegrationAspect extends CommonIntegrationLoggerAspect {

    @Pointcut("execution(public * com.booking.svc.repositories..*.*(..))")
    public void serviceIntegrationTrace() {
    }

    @Around("serviceIntegrationTrace()")
    public Object traceServiceIntegration(ProceedingJoinPoint joinPoint) throws Throwable {
        return trace(joinPoint);
    }

}
