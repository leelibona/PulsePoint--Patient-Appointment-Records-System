package com.pulsepoint;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * PulsePoint Application — Spring Boot Entry Point
 *
 * Starts the embedded Tomcat server on port 8080.
 * API is accessible at: http://localhost:8080/api
 * Swagger UI is accessible at: http://localhost:8080/swagger-ui.html
 */
@SpringBootApplication
public class PulsePointApplication {
    public static void main(String[] args) {
        SpringApplication.run(PulsePointApplication.class, args);
    }
}