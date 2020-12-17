package com.walker.rpc.aop.annotation;

/**
 * 服务提供者
 *
 * @author fcwalker
 * @date 2020/12/17 11:48
 **/
public @interface RpcService {
    String version() default "";
}
