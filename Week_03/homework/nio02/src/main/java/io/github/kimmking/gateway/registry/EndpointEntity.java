package io.github.kimmking.gateway.registry;

import lombok.Data;

@Data
public class EndpointEntity {

    private String name;
    private String uri;
    private int weight;
}
