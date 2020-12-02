package com.walker.insert;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.orm.jpa.JpaTransactionManager;

/**
 * 批量插入测试
 * 
 * @author dell
 */
@SpringBootApplication
public class InsertTest {

    public static void main(String[] args) {
        SpringApplication.run(InsertTest.class);
    }
}
