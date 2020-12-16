package com.walker.core.proxy.server;

/**
 * @author fcwalker
 * 调用方法的解释器
 * 由服务提供方进行实现（服务提供方可根据项目开发环境自行实现）
 */
public interface RpcServerResolver {
    /**
     * 反射生成实现类
     * @param serviceClassName
     * @return
     */
    Object resolve(String serviceClassName);
}
