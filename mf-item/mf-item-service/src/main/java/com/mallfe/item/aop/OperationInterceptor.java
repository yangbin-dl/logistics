package com.mallfe.item.aop;

import com.mallfe.common.json.JsonObject;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Slf4j
public class OperationInterceptor {
    private final JsonMapper jsonMapper = JsonMapper.buildNonNullMapper();

    @Pointcut("execution(* com.mallfe.item.controller*..*Controller.*(..))")
    public void log() {
    }

    @Around("log()")
    public Object around(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        String method = proceedingJoinPoint.getSignature().getName();
        String action = proceedingJoinPoint.getTarget().getClass().getSimpleName();
        log.info(String.format("Request: %s.%s  参数: %s", action, method, jsonMapper.toJson(proceedingJoinPoint.getArgs())));
        long before = System.currentTimeMillis();
        ResponseEntity result;
        result = (ResponseEntity) proceedingJoinPoint.proceed();
        log.info(String.format("Response: %s.%s  耗时: %s ms 返回: %s", action, method, System.currentTimeMillis() - before, jsonMapper.toJson(result)));
        return result;
    }

}
