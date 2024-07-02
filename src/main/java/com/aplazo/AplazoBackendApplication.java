package com.aplazo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@ComponentScan(basePackages = "com.aplazo.controller")
@EnableJpaRepositories("com.aplazo.repository")
@ComponentScan(basePackages = "com.aplazo.service")
@ComponentScan(basePackages = "com.aplazo.repository")
public class AplazoBackendApplication {
    public static void main(String[] args) {
        SpringApplication.run(AplazoBackendApplication.class, args);
    }
}