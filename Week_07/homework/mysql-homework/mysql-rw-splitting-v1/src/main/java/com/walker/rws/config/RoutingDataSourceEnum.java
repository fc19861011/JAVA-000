package com.walker.rws.config;

/**
 * 数据源枚举
 * @author fcwalker
 */
public enum RoutingDataSourceEnum {
    MASTER_DS,
    SLAVE_DS;

    RoutingDataSourceEnum() {

    }

    public static RoutingDataSourceEnum findByCode(String dbRouting) {
        for (RoutingDataSourceEnum e : values()) {
            if (e.name().equals(dbRouting)) {
                return e;
            }
        }
        return MASTER_DS;
    }
}
