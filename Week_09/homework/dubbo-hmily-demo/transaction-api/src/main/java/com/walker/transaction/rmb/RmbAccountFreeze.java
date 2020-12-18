package com.walker.transaction.rmb;

import lombok.Data;

/**
 * @author fcwalker
 * @date 2020/12/18 16:41
 **/
@Data
public class RmbAccountFreeze {
    private Integer userId;
    private Integer account;
}
