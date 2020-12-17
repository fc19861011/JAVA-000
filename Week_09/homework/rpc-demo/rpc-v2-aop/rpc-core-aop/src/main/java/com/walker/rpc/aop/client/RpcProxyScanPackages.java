package com.walker.rpc.aop.client;

import com.walker.rpc.aop.annotation.RpcReference;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.ResourceLoaderAware;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternUtils;
import org.springframework.core.type.classreading.CachingMetadataReaderFactory;
import org.springframework.core.type.classreading.MetadataReader;
import org.springframework.core.type.classreading.MetadataReaderFactory;
import org.springframework.util.Assert;
import org.springframework.util.ClassUtils;
import org.springframework.util.ConcurrentReferenceHashMap;
import org.springframework.util.SystemPropertyUtils;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.*;

/**
 * 扫描指定package，并筛选出有@RpcReference注解属性的类
 *
 * @author fcwalker
 * @date 2020/12/17 15:39
 **/
public class RpcProxyScanPackages implements ResourceLoaderAware {

    /**
     * Spring容器注入
     */
    private static ResourceLoader resourceLoader;

    /**
     * 用于缓存处理过程中Field的@RpcReference注解信息
     */
    public final static Map<Class<?>, Map<Field, RpcReference>> clsRpcReferences =
            new ConcurrentReferenceHashMap<>(10);

    @Override
    public void setResourceLoader(ResourceLoader resourceLoader) {
        RpcProxyScanPackages.resourceLoader = resourceLoader;
    }

    public static void register(BeanDefinitionRegistry registry, Collection<String> packageNames) {
        Assert.notNull(registry, "Registry must not be null");
        Assert.notNull(packageNames, "PackageNames must not be null");
        packageNames.stream().forEach(packageName -> scanPackage(packageName));
    }

    /**
     * 扫描指定package下非抽象类或接口的类
     * 
     * @param packageName
     */
    private static void scanPackage(String packageName) {
        Set<Class<?>> classes = new HashSet<>();
        ResourcePatternResolver resolver =
                ResourcePatternUtils.getResourcePatternResolver(resourceLoader);
        MetadataReaderFactory metaReader = new CachingMetadataReaderFactory(resourceLoader);
        Resource[] resources;
        try {
            String packageSearchPath =
                    ResourcePatternResolver.CLASSPATH_ALL_URL_PREFIX.concat(ClassUtils
                            .convertClassNameToResourcePath(
                                    SystemPropertyUtils.resolvePlaceholders(packageName))
                            .concat("/**/*.class"));
            resources = resolver.getResources(packageSearchPath);
            for (Resource r : resources) {
                if (r.isReadable()) {
                    MetadataReader reader = metaReader.getMetadataReader(r);
                    try {
                        // 当类型不是抽象类或接口在添加到集合
                        if (reader.getClassMetadata().isConcrete()) {
                            classes.add(Class.forName(reader.getClassMetadata().getClassName()));
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        Assert.notEmpty(classes, "没有扫描到可以注册的类");
        // 动态代理类
        proxyBeanServiceField(classes);
    }

    /**
     * 代理类中@RpcReference的属性对象
     * 
     * @param classes
     */
    private static void proxyBeanServiceField(Set<Class<?>> classes) {
        long count = classes.stream().filter(cls -> screenCls(cls)).count();
        System.out.println("需要代理的类有" + count + "个");
    }

    private static boolean screenCls(Class<?> cls) {
        Map<Field, RpcReference> fieldReferenceMap = clsRpcReferences.get(cls);
        if (null == fieldReferenceMap) {
            fieldReferenceMap = new ConcurrentReferenceHashMap(10);
            clsRpcReferences.put(cls, fieldReferenceMap);
        }
        final Map<Field, RpcReference> fieldReferenceMap2 = fieldReferenceMap;
        return Arrays.stream(cls.getDeclaredFields())
                .filter(field -> judgeField(field, fieldReferenceMap2)).count() > 0;
    }

    /**
     * 判断Field是否包含@RpcReference注解，如果存在则缓存
     * 
     * @param field
     * @return
     */
    private static boolean judgeField(Field field, Map<Field, RpcReference> fieldRpcReferences) {
        RpcReference[] reference = field.getAnnotationsByType(RpcReference.class);
        if (reference.length > 0) {
            fieldRpcReferences.put(field, reference[0]);
            return true;
        }
        return false;
    }


}
