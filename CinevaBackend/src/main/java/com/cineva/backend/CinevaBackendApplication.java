package com.cineva.backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
public class CinevaBackendApplication extends SpringBootServletInitializer {

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(CinevaBackendApplication.class);
    }

    public static void main(String[] args) {
        SpringApplication.run(CinevaBackendApplication.class, args);
    }

    // ✅ Added this controller to fix the 404 issue
    @RestController
    static class HomeController {
        @GetMapping("/")
        public String home() {
            return "Welcome to Cineva Backend API!";
        }
    }
}
