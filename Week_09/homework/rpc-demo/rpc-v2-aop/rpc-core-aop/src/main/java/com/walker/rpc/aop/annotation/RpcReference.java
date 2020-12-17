package com.walker.rpc.aop.annotation;

import java.lang.annotation.*;

/**
 * 远程方法代理
 *
 * @author fcwalker
 * @date 2020/12/17 11:47
 **/
@Target({ElementType.PARAMETER, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface RpcReference {
    String version() default "";

    String url() default "";
}
