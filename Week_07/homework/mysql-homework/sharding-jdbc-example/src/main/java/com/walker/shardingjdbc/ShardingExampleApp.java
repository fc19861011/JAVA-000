package com.walker.shardingjdbc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Hello world!
 *
 */
@SpringBootApplication
public class ShardingExampleApp
{
    public static void main( String[] args )
    {
        SpringApplication.run(ShardingExampleApp.class);
    }

//    @Bean
//    public DataSource dataSource() {
//        // 配置真实数据源
//        Map<String, DataSource> dataSourceMap = new HashMap<>(3);
//
//        // 配置第 1 个数据源
//        HikariDataSource primaryDataSource = new HikariDataSource();
//        primaryDataSource.setDriverClassName("com.mysql.jdbc.Driver");
//        primaryDataSource.setJdbcUrl("jdbc:mysql://127.0.0.1:3306/shop?useUnicode=true&characterEncoding=UTF-8&zeroDateTimeBehavior=convertToNull&allowMultiQueries=true&serverTimezone=UTC");
//        primaryDataSource.setUsername("root");
//        primaryDataSource.setPassword("root");
//        dataSourceMap.put("pd", primaryDataSource);
//
//        // 配置第 1 个数据源
//        HikariDataSource slaveDataSource1 = new HikariDataSource();
//        slaveDataSource1.setDriverClassName("com.mysql.jdbc.Driver");
//        slaveDataSource1.setJdbcUrl("jdbc:mysql://127.0.0.1:3307/shop?useUnicode=true&characterEncoding=UTF-8&zeroDateTimeBehavior=convertToNull&allowMultiQueries=true&serverTimezone=UTC");
//        slaveDataSource1.setUsername("root");
//        slaveDataSource1.setPassword("root");
//        dataSourceMap.put("sd1", slaveDataSource1);
//
//        // 配置第 1 个数据源
//        HikariDataSource slaveDataSource2 = new HikariDataSource();
//        slaveDataSource2.setDriverClassName("com.mysql.jdbc.Driver");
//        slaveDataSource2.setJdbcUrl("jdbc:mysql://127.0.0.1:3308/shop?useUnicode=true&characterEncoding=UTF-8&zeroDateTimeBehavior=convertToNull&allowMultiQueries=true&serverTimezone=UTC");
//        slaveDataSource2.setUsername("root");
//        slaveDataSource2.setPassword("root");
//        dataSourceMap.put("sd2", slaveDataSource2);
//
//        ShardingTableRuleConfiguration orderTableRuleConfig = new ShardingTableRuleConfiguration("t_test", "ds${0..1}.t_test_${0..1}");
//        ShardingRuleConfiguration shardingRuleConfig = new ShardingRuleConfiguration();
//        shardingRuleConfig.getTables().add(orderTableRuleConfig);
//
//        DataSource dataSource = null;
//        try {
//            dataSource = ShardingSphereDataSourceFactory.createDataSource(dataSourceMap, Collections.singleton(shardingRuleConfig), new Properties());
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//        return dataSource;
//    }
}
