package com.cloud.common.core.aop;

import com.cloud.common.core.annotation.PerformanceMonitor;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class PerformanceMonitorAop {

    private Logger LOGGER = LoggerFactory.getLogger(PerformanceMonitorAop.class);

    /** com.cloud.api包或者子包下面的任何方法*/
    @Pointcut("execution(* com.cloud.api..*.*(..))")
    public void point() {
    }

    @Around("@annotation(monitor)")
    public Object monitor(ProceedingJoinPoint joinPoint, PerformanceMonitor monitor) {
        String methodName = joinPoint.getSignature().getName();
        Object[] args = joinPoint.getArgs();
        long start = System.currentTimeMillis();
        Object obj = null;
        try {
            obj = joinPoint.proceed(args);
        } catch (Throwable throwable) {
            LOGGER.error("统计[{}]方法耗时失败",methodName);
        }
        long end = System.currentTimeMillis();

        LOGGER.info(String.format("[%s]方法耗时 [%d] 毫秒",methodName,end-start));
        return obj;
    }
}
