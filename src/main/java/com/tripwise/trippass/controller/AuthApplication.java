package com.tripwise.trippass.controller;


import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.server.ResponseStatusException;

import java.util.Map;

/**
 * ================================================================
 * Package: com.tripwise.trippass.controller
 * Project : trippass
 * Author : Ochwada-GMK
 * Date :Thursday, 21. August.2025, 10:01
 * Description:  Controller for handling authentication-related routes such as login, dashboard, and home.
 * ================================================================
 */
@Controller
@Slf4j
public class AuthApplication {
    /**
     * Handles the login page request.
     *
     * @return the name of the login view template.
     */
    @GetMapping("/login")
    public String loginPage() {
        return "login"; // renders login.html
    }

    /**
     * Handles the dashboard page request after successful authentication.
     * Extracts user details from the OIDC user and adds them to the model.
     *
     * @param model the model to pass data to the view.
     * @param user  the authenticated OIDC user containing user attributes.
     * @return the name of the dashboard view template.
     */
    @GetMapping("/dashboard")
    public String dashboard(Model model, @AuthenticationPrincipal OidcUser user) {
        // Get the token value
        String token = user.getIdToken().getTokenValue();

        // Use the token for backend logic here (e.g. API call)
        // Get user's full name from OIDC attributes
        String fullName = user.getAttribute("name");
        String email = user.getEmail();
        String picture = user.getAttribute("picture");

        model.addAttribute("name", fullName);
        model.addAttribute("email", email);
        model.addAttribute("token", token);
        model.addAttribute("picture", picture);

        return "dashboard";
    }

    /**
     * Handles the home page request.
     * This route is public and does not require authentication.
     *
     * @return the name of the home view template ("home.html").
     */
    @GetMapping("/")
    public String home() {
        return "home";
    }

    /**
     * Exposes the authenticated user's raw ID token (JWT) via a simple HTTP GET endpoint.
     * *
     * This internal endpoint is intended for use by trusted services that need access to the currently authenticated
     * user's ID token.
     *
     * @param user   the authenticated {@link OidcUser} injected by Spring Security
     * @param userId (optional) the expected user ID (subject); can be used for validation
     * @return the raw ID token string issued by the identity provider (e.g. Google)
     * @throws ResponseStatusException if userId is provided and does not match the authenticated user's subject
     */
    @GetMapping("/token")
    @ResponseBody
    public String getToken(@AuthenticationPrincipal OidcUser user,
                           @RequestParam(name = "userId", required = false)
                           String userId) {
        // log the userId and also validate it
        log.info("Requested token for userId: {}", userId);

        if (userId != null && !user.getSubject().equals(userId)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Unauthorized access");
        }

        return user.getIdToken().getTokenValue();
    }
}
