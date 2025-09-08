package com.tripwise.trippass.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;

/**
 * ================================================================
 * Package: com.tripwise.trippass.config
 * Project : trippass
 * Author : Ochwada-GMK
 * Date :Thursday, 21. August.2025, 09:54
 * Description:  Used to define beans, settings, and application setup logic
 * ================================================================
 */
@Configuration
public class SecurityConfig {


    /**
     * defines the security filter chain for the application.
     *
     * @param httpSecurity the HttpSecurity object used to configure security features.
     * @return the configured SecurityFilterChain
     * @throws Exception if an error occurs while configuring security
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity)
            throws Exception {
        httpSecurity
                .csrf(AbstractHttpConfigurer::disable)
                .cors(cors -> {})
                // Authorization rules
                .authorizeHttpRequests(auth -> auth
                        // Allow unrestricted access to the home page, login page, and all OAuth2-related endpoints
                        .requestMatchers("/", "/login", "/oauth2/**", "/token").permitAll()
                        // All other endpoints require authentication
                        .anyRequest().authenticated()
                )
                // Enable OAuth2 Login
                .oauth2Login(oauth2 -> oauth2
                        // Custom login page path
                        .loginPage("/login")
                        // Redirect to dashboard after successful login
                        .defaultSuccessUrl("/dashboard", true)
                ); // enables Google login
        // Build and return the configured filter chain
        return httpSecurity.build();
    }
}
