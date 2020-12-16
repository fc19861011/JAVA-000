package com.walker.core.protocol;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author dell
 * @date 2020/12/15 16:59
 **/
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RpcProtoReq {
    private String className;
    private String methodName;
    private Object[] params;
    private String[] paramTypes;
}
