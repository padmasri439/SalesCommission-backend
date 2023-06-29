package com.example.springboot;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class ExecutionTime {
    Logger logger = LoggerFactory.getLogger(ExecutionTime.class);

    @Around("@annotation(com.example.springboot.TrackExecutionTime)")
    public Object trackTime(ProceedingJoinPoint pjp) throws Throwable{
        long startTime = System.currentTimeMillis();
        Object obj = pjp.proceed();
        long endTime = System.currentTimeMillis();
        logger.info("\nMethod name: " + pjp.getSignature()+"\nTime taken to execute: " + (endTime - startTime) + " ms");
        return obj;
    }
}
