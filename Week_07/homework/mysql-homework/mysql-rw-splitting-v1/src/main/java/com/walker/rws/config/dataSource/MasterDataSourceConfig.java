package com.walker.rws.config.dataSource;

import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.util.StringUtils;

/**
 * @author fcwalker
 */
@Configuration
public class MasterDataSourceConfig {
    /**
     * 指定主数据库的dataSource配置
     * 主数据库数据源的db配置前缀采用spring.master
     *
     * @return
     */
    @Primary
    @Bean(name = "masterDataSourceProperties")
    @ConfigurationProperties("spring.master")
    public DataSourceProperties dataSourceProperties() {
        return new DataSourceProperties();
    }

    /**
     * 可以选择不同的数据源，用HikariDataSource创建主库数据源
     * 配置数据源1所用的hikari配置key的前缀
     *
     * @param masterDataSourceProperties
     * @return
     */
    @Primary
    @Bean(name = "masterDataSource")
    @ConfigurationProperties(prefix = "spring.datasource.hikari.master")
    public HikariDataSource dataSource(@Qualifier("masterDataSourceProperties") DataSourceProperties masterDataSourceProperties) {
        HikariDataSource dataSource = masterDataSourceProperties.initializeDataSourceBuilder().type(HikariDataSource.class).build();
        if (StringUtils.hasText(masterDataSourceProperties.getName())) {
            dataSource.setPoolName(masterDataSourceProperties.getName());
        }
        return dataSource;
    }

}
