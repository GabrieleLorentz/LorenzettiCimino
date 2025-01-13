package com.example.s_and_c.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**") // Consente CORS su tutte le rotte
                .allowedOrigins("http://localhost:5173") // Consente solo richieste dal frontend
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS") // Metodi consentiti
                .allowedHeaders("*") // Consente tutti gli header
                .allowCredentials(true); // Consente credenziali (es. cookie o auth headers)
    }
}
