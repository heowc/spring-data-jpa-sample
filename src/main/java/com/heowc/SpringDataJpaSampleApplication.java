package com.heowc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class SpringDataJpaSampleApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringDataJpaSampleApplication.class, args);
    }
}
