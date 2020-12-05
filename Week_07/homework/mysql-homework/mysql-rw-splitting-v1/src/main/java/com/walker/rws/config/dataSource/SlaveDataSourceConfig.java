package com.walker.rws.config.dataSource;

import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.util.StringUtils;

/**
 * @author fcwalker
 */
@Configuration
public class SlaveDataSourceConfig {
    /**
     * 指定从数据库的dataSource配置
     * 从数据库的数据源的db配置前缀采用spring.slave
     * @return
     */
    @Bean(name = "slaveDataSourceProperties")
    @ConfigurationProperties("spring.slave")
    public DataSourceProperties dataSourceProperties() {
        return new DataSourceProperties();
    }

    /**
     * 可以选择不同的数据源，这里我用HikariDataSource举例，创建从库数据源
     * 配置数据源1所用的hikari配置key的前缀
     * @param db1DataSourceProperties
     * @return
     */
    @Bean(name = "slaveDataSource")
    @ConfigurationProperties(prefix = "spring.datasource.hikari.slave")
    public HikariDataSource dataSource(@Qualifier("slaveDataSourceProperties") DataSourceProperties db1DataSourceProperties) {
        HikariDataSource dataSource = db1DataSourceProperties.initializeDataSourceBuilder().type(HikariDataSource.class).build();
        if (StringUtils.hasText(db1DataSourceProperties.getName())) {
            dataSource.setPoolName(db1DataSourceProperties.getName());
        }
        return dataSource;
    }

}
