package com.walker.rpc.aop.server;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.walker.rpc.aop.protocol.RpcProtoReq;
import com.walker.rpc.aop.protocol.RpcProtoResp;
import com.walker.rpc.aop.server.ServiceCache;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;

/**
 * @author fcwalker 服务提供者根据调用方法的协议
 */
public class RpcServerInvoker {
    private RpcServerResolver resolver;

    public RpcServerInvoker(RpcServerResolver resolver) {
        this.resolver = resolver;
    }

    public RpcProtoResp invoke(final RpcProtoReq protoReq) {
        RpcProtoResp protoResp = new RpcProtoResp();
        String serviceClass = protoReq.getClassName();
        String methodName = protoReq.getMethodName();
        String[] paramTypes = protoReq.getParamTypes();
        Object target;
        if (ServiceCache.SERVICE_MAP.containsKey(serviceClass)) {
            target = ServiceCache.SERVICE_MAP.get(serviceClass);
        } else {
            target = this.resolver.resolve(serviceClass);
            ServiceCache.SERVICE_MAP.put(serviceClass, target);
        }
        Method targetMethod = resolveMethodFromClass(target.getClass(), methodName, paramTypes);
        try {
            Object[] params = protoReq.getParams();
            // 参数类型转换
            if (null != params && params.length > 0) {
                convertParams(params, paramTypes);
            }
            Object result = targetMethod.invoke(target, params);
            protoResp.setStatus(true);
            // 对返回对象进行序列化
            protoResp.setResult(JSON.toJSONString(result, SerializerFeature.WriteClassName));
        } catch (IllegalAccessException | InvocationTargetException | ClassNotFoundException e) {
            e.printStackTrace();
            protoResp.setStatus(false);
            protoResp.setException(e);
        }
        return protoResp;
    }

    /**
     * 根据参数类型格式化参数
     *
     * @param params
     * @param paramTypes
     */
    private void convertParams(Object[] params, String[] paramTypes) throws ClassNotFoundException {
        for (int i = 0; i < paramTypes.length; i++) {
            String type = paramTypes[i];
            Object param = params[i];
            Class<?> cls;
            cls = Class.forName(type);
            params[i] = JSON.parseObject(JSON.toJSONString(param), cls);
        }
    }

    private Method resolveMethodFromClass(final Class<?> kclass, final String methodName,
                                          final String[] paramTypes) {
        return Arrays.stream(kclass.getMethods())
                .filter(m -> compareMethod(m, methodName, paramTypes))
                .findFirst().get();
    }

    boolean compareMethod(final Method sourceMethod, final String targetMethodName,
                          final String[] paramTypes) {
        if (!sourceMethod.getName().equals(targetMethodName)) {
            return false;
        }
        if (null != paramTypes && paramTypes.length > 0) {
            return compareMethodWithParams(sourceMethod, paramTypes);
        }
        return true;
    }

    boolean compareMethodWithParams(final Method sourceMethod, final String[] paramTypes) {
        int paramsCount = sourceMethod.getParameterCount();
        if (paramsCount != paramTypes.length) {
            return false;
        }
        return Arrays.asList(sourceMethod.getParameterTypes()).stream()
                .allMatch(typeCls -> compareMethodParamType(typeCls, paramTypes));
    }

    boolean compareMethodParamType(final Class<?> typeCls, final String[] paramTypes) {
        return Arrays.asList(paramTypes).stream()
                .anyMatch(type -> type.equals(typeCls.getName()));
    }


}
