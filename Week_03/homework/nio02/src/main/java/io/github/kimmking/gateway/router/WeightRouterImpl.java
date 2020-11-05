package io.github.kimmking.gateway.router;

import io.github.kimmking.gateway.registry.EndpointEntity;
import io.github.kimmking.gateway.registry.RegistryCenter;
import io.github.kimmking.gateway.registry.VisitCounterEntity;

import java.util.List;

/**
 * @author fc
 * 权重
 */
public class WeightRouterImpl implements HttpEndpointRouter {
    @Override
    public String route(final String path) {
        List<EndpointEntity> endpoints = RegistryCenter.endpointMap.get(path);
        if(!endpoints.isEmpty()) {
            VisitCounterEntity visitCounterEntity = RegistryCenter.visitMap.get(path);
            visitCounterEntity.setTotalCount(visitCounterEntity.getTotalCount() + 1);
            int currentIndex = visitCounterEntity.getCurrentIndex();
            int currentCount = visitCounterEntity.getCurrentCount();
            EndpointEntity endpointEntity = endpoints.get(currentIndex);
            int weight = endpointEntity.getWeight();
            if(weight > currentCount) {
                visitCounterEntity.setCurrentCount(currentCount + 1);
                return endpointEntity.getUri();
            } else {
                if(currentIndex < endpoints.size() - 1) {
                    currentIndex ++;
                } else {
                    currentIndex = 0;
                }
                visitCounterEntity.setCurrentIndex(currentIndex);
                visitCounterEntity.setCurrentCount(1);
                endpointEntity = endpoints.get(currentIndex);
                return endpointEntity.getUri();
            }
        }
        return null;
    }
}
