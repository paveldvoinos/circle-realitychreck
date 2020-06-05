package com.transferwise;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.kafka.annotation.EnableKafka;

@Slf4j
@EnableKafka
@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        try {
            new SpringApplication(Application.class).run(args);
        } catch (Exception e) {
            log.error("Service shutting down because of exception.", e);
        }
    }
}
