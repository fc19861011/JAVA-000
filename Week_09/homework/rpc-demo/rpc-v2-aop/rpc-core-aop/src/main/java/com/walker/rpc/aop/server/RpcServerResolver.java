package com.walker.rpc.aop.server;

/**
 * @author fcwalker
 * 调用方法的解释器
 * 由服务提供方进行实现（服务提供方可根据项目开发环境自行实现）
 */
public interface RpcServerResolver {
    /**
     * 反射生成实现类
     * @param serviceClassName 调用类名
     * @return Object
     */
    Object resolve(String serviceClassName);
}
