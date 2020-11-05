package io.github.kimmking.gateway.registry;

import lombok.Data;

@Data
public class VisitCounterEntity {
    private String name;
    private int currentIndex;
    private int currentCount;
    private int totalCount;
}
