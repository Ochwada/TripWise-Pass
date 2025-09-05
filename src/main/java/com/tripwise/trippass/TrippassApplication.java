package com.tripwise.trippass;

import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * ================================================================
 * Package: com.tripwise.trippass.config
 * Project : trippass
 * Author : Ochwada-GMK
 * Date :Thu, 21. August.2025 , 09:50
 * Description:
 * ================================================================
 */
@SpringBootApplication
public class TrippassApplication {

    public static void main(String[] args) {
        SpringApplication.run(TrippassApplication.class, args);
    }

    static {
        // Load environment variables from .env file
        // Ignores file if missing (useful for production environments like Heroku)
        Dotenv dotenv = Dotenv.configure()
                .ignoreIfMissing()
                .load();

        // List of expected keys to load from the .env file
        String[] envVar = {
                "GOOGLE_CLIENT_ID",
                "GOOGLE_CLIENT_SECRET",
                "SPRING_SECURITY_OAUTH2_RESOURCESERVER_JWT_ISSUER_URI",
                "JWT_ALG",
                "JWT_SECRET"
        };

        // Iterate through keys and set them as JVM system properties if found
        for (String key : envVar) {
            String value = dotenv.get(key);

            if (value != null) {
                System.setProperty(key, value); // Makes it accessible via System.getProperty
                System.out.println("✅ " + key + " loaded and set.");
            } else {
                System.out.println("⚠️" + key + " not found in .env file. Skipping System.");
            }
        }
    }
}
