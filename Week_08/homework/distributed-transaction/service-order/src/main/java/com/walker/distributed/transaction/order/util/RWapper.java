package com.walker.distributed.transaction.order.util;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class RWapper<T> {
    private int code;
    private String msg;
    private T object;
}
