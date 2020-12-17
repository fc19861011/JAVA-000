package com.walker.rpc.aop.client;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.parser.ParserConfig;
import com.walker.rpc.aop.protocol.RpcProtoReq;
import com.walker.rpc.aop.protocol.RpcProtoResp;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

import java.io.IOException;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * 动态代理实现远程调用
 *
 * @author dell
 * @date 2020/12/15 16:52
 **/
public class RpcClientProxy {

    static {
        ParserConfig.getGlobalInstance().addAccept("com.walker");
    }

    public static <T> T create(final Class<T> serviceClass, final String url) {
        return (T) Proxy.newProxyInstance(RpcClientProxy.class.getClassLoader(), new Class[]{serviceClass}, new RpcInvocationHandler(serviceClass, url));
    }

    public static class RpcInvocationHandler implements InvocationHandler {
        public static final MediaType JSONTYPE = MediaType.get("application/json; charset=utf-8");

        private final Class<?> serviceClass;

        private final String url;

        public <T> RpcInvocationHandler(Class<T> serviceClass, String url) {
            this.serviceClass = serviceClass;
            this.url = url;
        }

        @Override
        public Object invoke(final Object proxy, final Method method, final Object[] args) throws Throwable {
            // 传输数据格式（协议内容），供序列化使用
            RpcProtoReq rpcProtoReq = RpcProtoReq.builder()
                    .className(this.serviceClass.getName())
                    .methodName(method.getName())
                    .build();
            if (null != args) {
                rpcProtoReq.setParams(args);
                String[] paramTypes = new String[args.length];
                for (int i = 0; i < args.length; i++) {
                    paramTypes[i] = args[i].getClass().getName();
                }
                rpcProtoReq.setParamTypes(paramTypes);
            }
            // 发起远程调用，并获取结果
            RpcProtoResp protoResp = call(rpcProtoReq, url);
            // 异常情况处理
            if (!protoResp.isStatus()) {
                Exception exception = protoResp.getException();
                System.err.println(exception.getMessage());
                throw exception;
            }
            // 解析结果并返回
            return JSON.parse(protoResp.getResult());
        }

        // 远程调用
        public RpcProtoResp call(final RpcProtoReq protoReq, final String url) {
            RpcProtoResp protoResp = null;
            // 远程访问
            // 通过http方式
            try {
                protoResp = callByOkHttp(protoReq, url);
            } catch (IOException e) {
                e.printStackTrace();
                protoResp.setStatus(false);
                protoResp.setException(e);
            }
            return protoResp;
        }

        private RpcProtoResp callByOkHttp(final RpcProtoReq protoReq, final String url) throws IOException {
            String req = JSON.toJSONString(protoReq);
            System.out.println("req：" + req);
            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder()
                    .url(url)
                    .post(RequestBody.create(JSONTYPE, req))
                    .build();
            String respJson = client.newCall(request).execute().body().string();
            System.out.println("resp：" + respJson);
            return JSON.parseObject(respJson, RpcProtoResp.class);
        }

    }
}
