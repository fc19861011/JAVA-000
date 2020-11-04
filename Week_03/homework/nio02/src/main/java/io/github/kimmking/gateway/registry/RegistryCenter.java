package io.github.kimmking.gateway.registry;

import io.github.kimmking.gateway.util.PropertiesUtil;
import lombok.extern.slf4j.Slf4j;

import java.util.*;
import java.util.stream.Collectors;

@Slf4j
public class RegistryCenter {
    public final static Map<String, List<EndpointEntity>> endpointMap = new HashMap<>(15);
    public final static Map<String, VisitCounterEntity> visitMap = new HashMap<>(15);

    public static void init() {
        String apiConfig = PropertiesUtil.getValue("registry.api");
        String[] apis = apiConfig.split("@@");
        if (apis.length > 0) {
            for (String api : apis) {
                String[] apiDetailAry = api.split("#");
                String registryName = apiDetailAry[0];
                String registryUri = apiDetailAry[1];
                int registryWeight = Integer.valueOf(apiDetailAry[2]);
                EndpointEntity endpointEntity = new EndpointEntity();
                endpointEntity.setName(registryName);
                endpointEntity.setUri(registryUri);
                endpointEntity.setWeight(registryWeight);
                List<EndpointEntity> list;
                if (endpointMap.containsKey(registryName)) {
                    list = endpointMap.get(registryName);
                    endpointMap.remove(registryName);
                } else {
                    list = new ArrayList<>();
                }
                list.add(endpointEntity);
                endpointMap.put(registryName, list);
            }
            endpointMap.forEach((key, endpoints) -> {
                log.info("成功注册接口：{}，接口数：{}", key, endpoints.size());
                // 按权重排序
                endpoints = endpoints.stream().sorted(Comparator.comparing(EndpointEntity::getWeight).reversed()).collect(Collectors.toList());
                // 初始化访问记录
                VisitCounterEntity counterEntity = new VisitCounterEntity();
                counterEntity.setName(key);
                counterEntity.setCurrentCount(0);
                counterEntity.setCurrentIndex(0);
                counterEntity.setTotalCount(0);
                visitMap.put(key, counterEntity);
            });
        }
    }
}
