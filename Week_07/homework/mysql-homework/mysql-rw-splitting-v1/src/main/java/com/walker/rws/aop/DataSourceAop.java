package com.walker.rws.aop;

import com.walker.rws.config.DataSourceRoutingHolder;
import com.walker.rws.config.RoutingDataSourceEnum;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class DataSourceAop {
    @Pointcut("@annotation(com.walker.rws.aop.DynamicDataSource)")
    public void serviceMethod() {
    }

    @Around("serviceMethod()")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
        String methodName = joinPoint.getSignature().getName();
        if (methodName.startsWith("query") || methodName.startsWith("find")) {
            DataSourceRoutingHolder.setBranchContext(RoutingDataSourceEnum.findByCode("SLAVE_DS"));
        } else {
            DataSourceRoutingHolder.setBranchContext(RoutingDataSourceEnum.findByCode("MASTER_DS"));
        }
        Object result = joinPoint.proceed();
        DataSourceRoutingHolder.clearBranchContext();
        return result;
    }

}
