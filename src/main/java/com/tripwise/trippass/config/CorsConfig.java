package com.tripwise.trippass.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.*;


/**
 * ================================================================
 * Package Name: com.tripwise.trippass.config
 * Author      : Ochwada-GMK
 * Project Name: trippass
 * Date        : Monday,  08.Sept.2025 | 14:33
 * Description :
 * ================================================================
 */
@Configuration
public class CorsConfig {
    @Bean
    public org.springframework.web.cors.CorsConfigurationSource corsConfigurationSource() {
        var cfg = new org.springframework.web.cors.CorsConfiguration();
        cfg.setAllowedOrigins(List.of("http://localhost:9094", "http://localhost:9095"));
        cfg.setAllowedMethods(List.of("GET","OPTIONS"));
        cfg.setAllowedHeaders(List.of("Content-Type","Authorization"));
        cfg.setAllowCredentials(true); // only if you need cookies

        var src = new org.springframework.web.cors.UrlBasedCorsConfigurationSource();
        src.registerCorsConfiguration("/token", cfg);
        return src;
    }
}
