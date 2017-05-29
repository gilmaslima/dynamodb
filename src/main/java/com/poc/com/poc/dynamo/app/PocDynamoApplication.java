package com.poc.com.poc.dynamo.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * Hello world!
 */
@SpringBootApplication
@EnableAutoConfiguration
@ComponentScan("com.poc.com.poc.dynamo.*")
public class PocDynamoApplication {

    public static void main(String[] args) {
        SpringApplication.run(PocDynamoApplication.class, args);
    }
}
