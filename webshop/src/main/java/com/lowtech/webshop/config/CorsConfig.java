package com.lowtech.webshop.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
/**
 * @author Paul Barthel
 * @date 19.01.2025
 * @time 22:20
 */
@Configuration
@EnableWebMvc
public class CorsConfig implements WebMvcConfigurer {

    // Inject allowed origins from a property (comma-separated if more than one)
    @Value("${app.cors.allowedOrigins}")
    private String[] allowedOrigins;

    @Bean
    public WebMvcConfigurer corsConfigurerBean() {
        return new WebMvcConfigurer() {
            @Override
            @SuppressWarnings("NullableProblems")
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**")
                        .allowedOrigins(allowedOrigins)
                        .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                        .allowedHeaders("*");
            }
        };
    }

    // Hier überschreibst du addResourceHandlers im *eigenen* Config-Klassenkontext
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/images/**")
                .addResourceLocations("classpath:/images/");
    }
}