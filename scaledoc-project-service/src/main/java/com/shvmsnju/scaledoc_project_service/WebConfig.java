package com.shvmsnju.scaledoc_project_service;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import jakarta.validation.constraints.NotNull;

@Configuration
@EnableWebMvc
public class WebConfig implements WebMvcConfigurer {

    @SuppressWarnings("null")
    @Override
    public void addCorsMappings(@NotNull CorsRegistry registry) {
        registry.addMapping("/**").allowedOrigins("http://localhost:3000")
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS");

    }
}