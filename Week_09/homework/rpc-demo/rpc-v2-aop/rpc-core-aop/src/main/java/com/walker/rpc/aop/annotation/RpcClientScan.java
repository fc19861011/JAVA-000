package com.walker.rpc.aop.annotation;

import com.walker.rpc.aop.client.PpcProxyRegistrar;
import org.springframework.context.annotation.Import;
import org.springframework.core.annotation.AliasFor;

import java.lang.annotation.*;

/**
 * @author fcwalker
 * @date 2020/12/17 11:47
 **/
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import({PpcProxyRegistrar.class})
public @interface RpcClientScan {
    @AliasFor("basePackages")
    String[] value() default {};

    @AliasFor("value")
    String[] basePackages() default {};
}
