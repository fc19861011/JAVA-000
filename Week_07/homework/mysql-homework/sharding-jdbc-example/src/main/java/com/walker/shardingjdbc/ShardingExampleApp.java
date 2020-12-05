package com.walker.shardingjdbc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

/**
 * Hello world!
 *
 */
@EntityScan("com.walker.rws.domain")
@SpringBootApplication
public class ShardingExampleApp
{
    public static void main( String[] args )
    {
        SpringApplication.run(ShardingExampleApp.class);
    }
}
