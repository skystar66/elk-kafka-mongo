package com.xcar360;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.kafka.KafkaAutoConfiguration;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class,KafkaAutoConfiguration.class})
@EnableDiscoveryClient
@ServletComponentScan
public class ELKKafkaApplication {
    public static void main(String[] args) {
        SpringApplication.run(ELKKafkaApplication.class, args);
    }
}






