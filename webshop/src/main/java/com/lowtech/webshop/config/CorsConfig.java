package com.lowtech.webshop.config;

import com.lowtech.webshop.util.Filters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author Paul Barthel
 * @date 19.01.2025
 * @time 22:20
 */
@Configuration
@EnableWebMvc
public class CorsConfig {

    @Autowired
    private Environment environment;

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**")
                        .allowedOrigins(Filters.filterNotNull(
                                "http://localhost:5173",
                                environment.getProperty("env.data.imageS3url")
                        )) // Allow data origins
                        .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS") // Specify allowed HTTP methods
                        .allowedHeaders("*"); // Allow all headers
            }
        };
    }
}
