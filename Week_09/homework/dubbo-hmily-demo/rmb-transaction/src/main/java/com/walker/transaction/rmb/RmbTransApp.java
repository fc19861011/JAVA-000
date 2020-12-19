package com.walker.transaction.rmb;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.data.mongo.MongoDataAutoConfiguration;
import org.springframework.boot.autoconfigure.mongo.MongoAutoConfiguration;
import org.springframework.context.annotation.ImportResource;

/**
 * @author fcwalker
 * @date 2020/12/19 14:24
 **/
@SpringBootApplication(exclude = {MongoAutoConfiguration.class, MongoDataAutoConfiguration.class})
@ImportResource({"classpath:applicationContext.xml"})
public class RmbTransApp {

    public static void main(String[] args) {
        SpringApplication.run(RmbTransApp.class, args);
    }
}
