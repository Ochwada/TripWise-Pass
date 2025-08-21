package com.tripwise.trippass;
import org.springframework.security.oauth2.jwt.Jwt;

import java.time.Instant;
import java.util.Map;

/**
 * ================================================================
 * Package: com.tripwise.trippass
 * Project : trippass
 * Author : Ochwada-GMK
 * Date :Thursday, 21. August.2025, 10:31
 * Description: Simple test helper to build a Jwt stub for unit tests.
 * ================================================================
 */
public class TestJwt {
    public static Jwt with(
            String sub,
            String email,
            String name) {
        return new Jwt(
                "fake-token-value",
                Instant.now().minusSeconds(60),
                Instant.now().plusSeconds(3600),
                Map.of("alg", "none"),
                Map.of("sub", sub, "email", email, "name", name)
        );
    }
}
