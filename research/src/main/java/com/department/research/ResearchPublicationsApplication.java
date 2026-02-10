package com.department.research;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class ResearchPublicationsApplication {

    public static void main(String[] args) {
        SpringApplication.run(ResearchPublicationsApplication.class, args);
        System.out.println("\n===========================================");
        System.out.println("Research Publications System Started!");
        System.out.println("Access at: http://localhost:8080");
        System.out.println("===========================================\n");
    }
}
