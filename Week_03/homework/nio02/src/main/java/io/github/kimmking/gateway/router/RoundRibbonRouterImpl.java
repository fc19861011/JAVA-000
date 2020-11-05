package io.github.kimmking.gateway.router;

import io.github.kimmking.gateway.registry.EndpointEntity;
import io.github.kimmking.gateway.registry.RegistryCenter;
import io.github.kimmking.gateway.registry.VisitCounterEntity;

import java.util.List;

/**
 * @author fc
 * 轮询
 */
public class RoundRibbonRouterImpl implements HttpEndpointRouter {
    @Override
    public String route(final String path) {
        List<EndpointEntity> endpoints = RegistryCenter.endpointMap.get(path);
        if (!endpoints.isEmpty()) {
            VisitCounterEntity visitCounterEntity = RegistryCenter.visitMap.get(path);
            visitCounterEntity.setTotalCount(visitCounterEntity.getTotalCount() + 1);
            int currentIndex = visitCounterEntity.getCurrentIndex();
            int currentCount = visitCounterEntity.getCurrentCount();
            if (currentIndex < endpoints.size() - 1 && currentCount > 0) {
                currentIndex++;
            } else {
                currentIndex = 0;
            }
            visitCounterEntity.setCurrentIndex(currentIndex);
            visitCounterEntity.setCurrentCount(1);
            EndpointEntity endpointEntity = endpoints.get(currentIndex);
            return endpointEntity.getUri();
        }
        return null;
    }
}
