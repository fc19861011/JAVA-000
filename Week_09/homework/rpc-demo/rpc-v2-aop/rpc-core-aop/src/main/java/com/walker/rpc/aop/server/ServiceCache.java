package com.walker.rpc.aop.server;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author fcwalker
 */
public class ServiceCache {
    public final static Map<String, Object> SERVICE_MAP = new ConcurrentHashMap<>(10);
}
