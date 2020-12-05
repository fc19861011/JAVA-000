package com.walker.rws.config;

import com.google.common.collect.Maps;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.util.Map;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(
        //数据源的repository的包路径
        basePackages = {"com.walker.rws.repository"},
        entityManagerFactoryRef = "routingEntityManagerFactory",
        transactionManagerRef = "routingTransactionManager"
)
public class RoutingDataSourceConfig {

    @Autowired
    @Qualifier("masterDataSource")
    private DataSource masterDataSource;

    @Autowired
    @Qualifier("slaveDataSource")
    private DataSource slaveDataSource;

    /**
     * 创建RoutingDataSource，引用我们之前配置的db1DataSource和db2DataSource
     *
     * @return
     */
    @Bean(name = "routingDataSource")
    public DataSource dataSource() {
        Map<Object, Object> dataSourceMap = Maps.newHashMap();
        dataSourceMap.put(RoutingDataSourceEnum.MASTER_DS, masterDataSource);
        dataSourceMap.put(RoutingDataSourceEnum.SLAVE_DS, slaveDataSource);
        RoutingDataSource routingDataSource = new RoutingDataSource();
        //设置RoutingDataSource的默认数据源
        routingDataSource.setDefaultTargetDataSource(masterDataSource);
        //设置RoutingDataSource的数据源列表
        routingDataSource.setTargetDataSources(dataSourceMap);
        return routingDataSource;
    }
    /**
     * 类似db1和db2的配置，唯一不同的是，这里采用routingDataSource
     * @param builder
     * @param routingDataSource entityManager依赖routingDataSource
     * @return
     */
    @Bean(name = "routingEntityManagerFactory")
    public LocalContainerEntityManagerFactoryBean entityManagerFactory(EntityManagerFactoryBuilder builder, @Qualifier("routingDataSource") DataSource routingDataSource) {
        //数据routing的实体所在的路径，这里我们覆盖db1和db2的路径
        return builder.dataSource(routingDataSource).packages("com.walker.rws.domain")
                // persistenceUnit的名字采用db-routing
                .persistenceUnit("db-routing")
                .build();
    }
    /**
     * 配置数据的事务管理者，命名为routingTransactionManager依赖routtingEntityManagerFactory
     *
     * @param routingEntityManagerFactory
     * @return
     */
    @Bean(name = "routingTransactionManager")
    public PlatformTransactionManager transactionManager(@Qualifier("routingEntityManagerFactory") EntityManagerFactory routingEntityManagerFactory) {
        return new JpaTransactionManager(routingEntityManagerFactory);
    }

}
