package com.tripwise.trippass;


import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.core.oidc.OidcIdToken;
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;

import java.time.Instant;
import java.util.List;
import java.util.Map;

/**
 * ================================================================
 * Package: com.tripwise.trippass
 * Project : trippass
 * Author : Ochwada-GMK
 * Date :Thursday, 21. August.2025, 10:32
 * Description: Simple test helper to build an OidcUser stub for unit tests.
 * ================================================================
 */
public class TestOidcUser {
    public static DefaultOidcUser with(
            String sub,
            String name,
            String email,
            String picture,
            String token) {

        OidcIdToken idToken = new OidcIdToken(
                token,
                Instant.now().minusSeconds(60),
                Instant.now().plusSeconds(3600),
                Map.of("sub", sub, "name", name, "email", email, "picture", picture)
        );
        return new DefaultOidcUser(
                List.of(new SimpleGrantedAuthority("ROLE_USER")),
                idToken
        );
    }
}
