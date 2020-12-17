package com.walker.rpc.aop.client;

import com.walker.rpc.aop.annotation.RpcClientScan;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.annotation.AnnotationAttributes;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.util.Assert;
import org.springframework.util.ClassUtils;
import org.springframework.util.StringUtils;

import java.util.*;

/**
 * 动态将添加了@RpcReference注解的Field进行实例化
 *
 * @author fcwalker
 * @date 2020/12/17 15:22
 **/
public class PpcProxyRegistrar implements ImportBeanDefinitionRegistrar {

    @Override
    public void registerBeanDefinitions(AnnotationMetadata metadata,
            BeanDefinitionRegistry registry) {
        RpcProxyScanPackages.register(registry, (Collection) this.getPackagesToScan(metadata));
    }

    private Set<String> getPackagesToScan(AnnotationMetadata metadata) {
        AnnotationAttributes attributes = AnnotationAttributes
                .fromMap(metadata.getAnnotationAttributes(RpcClientScan.class.getName()));
        String[] basePackages = attributes.getStringArray("basePackages");
        Set<String> packagesToScan = new LinkedHashSet(Arrays.asList(basePackages));
        if (packagesToScan.isEmpty()) {
            String packageName = ClassUtils.getPackageName(metadata.getClassName());
            Assert.state(!StringUtils.isEmpty(packageName),
                    "@EntityScan cannot be used with the default package");
            return Collections.singleton(packageName);
        } else {
            return packagesToScan;
        }
    }
}
