package com.walker.rpc.aop.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;

/**
 * @author fcwalker
 * @date 2020/12/17 14:31
 **/
public class ReferenceAspect {

    @Pointcut("@annotation(com.walker.rpc.aop.annotation.RpcReference)")
    public void referenceMethod(){}

    @Before("referenceMethod()")
    public void initService(ProceedingJoinPoint joinPoint) {

    }

}
