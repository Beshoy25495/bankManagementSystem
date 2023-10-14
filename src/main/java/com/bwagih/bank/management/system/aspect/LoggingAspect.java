package com.bwagih.bank.management.system.aspect;


import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Aspect
@Order(1)
@Component
public class LoggingAspect {


    Logger log = LoggerFactory.getLogger(LoggingAspect.class);


    @Pointcut(value = "execution(* com.bwagih.bank.management.system.repository.*.*(..))")
    public void forRepositoryLog() {
    }

    @Pointcut(value = "execution(* com.bwagih.bank.management.system.service.*.*(..))")
    public void forServiceLog() {
    }

    @Pointcut(value = "execution(* com.bwagih.bank.management.system.controller.*.*(..))")
    public void forControllerLog() {
    }

    @Pointcut(value = "forRepositoryLog() || forServiceLog() || forControllerLog()")
    public void forAllApp() {
    }

    @Before(value = "forAllApp()")
    public void beforeMethod(JoinPoint joinPoint) {

        String methodName = joinPoint.getSignature().toShortString();

        log.info("====>  Method Name is >> {}", methodName);

        Object[] args = joinPoint.getArgs();

        Arrays.stream(args).forEach(arg -> log.debug("===> argument >> {}", arg));

    }

}
