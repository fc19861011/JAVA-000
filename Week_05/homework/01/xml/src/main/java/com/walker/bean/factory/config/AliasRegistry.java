package com.walker.bean.factory.config;

/**
 * bean别名支持接口类
 */
public interface AliasRegistry {
    /**
     * 注册别名
     * @param name
     * @param alias
     */
    void registerAlias(String name, String alias);

    /**
     * 批量删除别名
     * @param alias
     */
    void removeAlias(String alias);

    /**
     * 判断是否为别名
     * @param name
     * @return
     */
    boolean isAlias(String name);

    /**
     * 根据name获取所有别名
     * @param name
     * @return
     */
    String[] getAliases(String name);
}
