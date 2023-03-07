package com.example.emptask;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.PropertySource;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
@PropertySource({"classpath:camunda-engine-url.properties"})
public class EmpTaskApplication {

    public static void main(String[] args) {
        SpringApplication.run(EmpTaskApplication.class, args);
    }

    @Bean
    public RestTemplate getRestTemplate() {
        // Return a RestTemplat instance which will be used to call other external microservices
        return new RestTemplate();
    }

}
