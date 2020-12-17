package com.walker.core.exception;

/**
 * @author fcwalker
 * Rpc 异常
 */
public class RpcException extends RuntimeException {

    RpcException(String msg) {
        super(msg);
    }
}
