package com.walker.rws;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.transaction.jta.JtaAutoConfiguration;

/**
 * Hello world!
 *
 */
@EntityScan("com.walker.rws.domain")
@SpringBootApplication(exclude = JtaAutoConfiguration.class)
public class DynamicDataSourceApp
{
    public static void main( String[] args )
    {
        SpringApplication.run(DynamicDataSourceApp.class);
    }
}
