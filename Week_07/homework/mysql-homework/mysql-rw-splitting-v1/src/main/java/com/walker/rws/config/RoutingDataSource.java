package com.walker.rws.config;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;
import org.springframework.lang.Nullable;

import java.util.Optional;

/**
 * 动态数据源管理
 */
public class RoutingDataSource extends AbstractRoutingDataSource {
    @Nullable
    @Override
    protected Object determineCurrentLookupKey() {
        return Optional.ofNullable(DataSourceRoutingHolder.getBranchContext()).orElse(RoutingDataSourceEnum.MASTER_DS);
    }
}
