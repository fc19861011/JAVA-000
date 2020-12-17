package com.walker.rpc.aop.server;

import com.walker.rpc.aop.protocol.RpcProtoReq;
import com.walker.rpc.aop.protocol.RpcProtoResp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author fcwalker
 * @date 2020/12/17 20:45
 **/
@RestController
public class RemoteApi {
    @Autowired
    RpcServerInvoker invoker;

    @PostMapping("/")
    public RpcProtoResp get(@RequestBody RpcProtoReq req) {
        return invoker.invoke(req);
    }
}
