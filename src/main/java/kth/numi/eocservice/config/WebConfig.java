package kth.numi.eocservice.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig {
    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**") // all endpoints
                        .allowedOrigins("https://eoc-service.app.cloud.cbh.kth.se")
                        .allowedMethods("GET", "POST", "PUT", "PATCH")
                        .allowCredentials(true);
            }
        };
    }
}