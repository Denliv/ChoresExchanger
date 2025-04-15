package com.omsu.chores_exchanger;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories
public class Application {
    //Платформа для обмена бытовыми услугами между соседями
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
