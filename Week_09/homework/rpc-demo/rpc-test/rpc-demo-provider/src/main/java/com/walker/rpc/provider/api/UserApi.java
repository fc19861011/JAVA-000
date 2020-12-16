package com.walker.rpc.provider.api;

import com.walker.core.protocol.RpcProtoReq;
import com.walker.core.protocol.RpcProtoResp;
import com.walker.core.proxy.server.RpcServerInvoker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;

/**
 * @author fcwalker
 * @date 2020/12/16 17:09
 **/
@RestController
@ControllerAdvice
public class UserApi {

    @Autowired
    @Qualifier("invokerByReflex")
    RpcServerInvoker invoker;

    @Autowired
    @Qualifier("invokerByServiceCls")
    RpcServerInvoker invokerByCls;

    @PostMapping("/")
    public RpcProtoResp get(@RequestBody RpcProtoReq req) {
        return invoker.invoke(req);
    }

    @PostMapping("/cls")
    public RpcProtoResp getBycls(@RequestBody RpcProtoReq req) {
        return invokerByCls.invoke(req);
    }

    @ExceptionHandler(value = Exception.class)
    public RpcProtoResp bizExceptionHandler(Exception e){
        return RpcProtoResp.builder().status(false).exception(e).build();
    }
}
