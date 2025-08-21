package com.tripwise.trippass;

import com.tripwise.trippass.controller.AuthApplication;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.ui.ConcurrentModel;
import org.springframework.ui.Model;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.springframework.web.server.ResponseStatusException;

/**
 * ================================================================
 * Package: com.tripwise.trippass.controller
 * Project : trippass
 * Author : Ochwada-GMK
 * Date :Thursday, 21. August.2025, 10:25
 * Description:   Unit tests for {@link AuthApplication} controller endpoints.
 * ================================================================
 */

@SpringBootTest
class TrippassApplicationTests {

    private final AuthApplication controller = new AuthApplication();

    /**
     * Verifies that the home endpoint ("/") returns the "home" view name.
     */
    @Test
    void home_returnsHomeViewName() {
        assertThat(controller.home()).isEqualTo("home");
    }


    /**
     * Verifies that the login endpoint ("/login") returns the "login" view name.
     */
    @Test
    void login_returnsLoginViewName() {
        assertThat(controller.loginPage()).isEqualTo("login");
    }


    /**
     * Verifies that the dashboard endpoint ("/dashboard") populates the model
     * with OIDC user details (name, email, token, picture) and returns "dashboard".
     */
    @Test
    void dashboard_addsAttributesAndReturnsDashboardView() {
        // fake OIDC user using a stub
        var user = TestOidcUser.with(
                "123", "Jane Doe", "jane@example.com", "pic-url", "token-123"
        );
        Model model = new ConcurrentModel();

        String view = controller.dashboard(model, user);

        assertThat(view).isEqualTo("dashboard");
        assertThat(model.getAttribute("name")).isEqualTo("Jane Doe");
        assertThat(model.getAttribute("email")).isEqualTo("jane@example.com");
        assertThat(model.getAttribute("token")).isEqualTo("token-123");
        assertThat(model.getAttribute("picture")).isEqualTo("pic-url");
    }

    /**
     * Verifies that the /token endpoint returns the ID token string
     * when the userId is either not provided or matches the authenticated user.
     */
    @Test
    void token_returnsToken_whenUserIdMatchesOrNotProvided() {
        var user = TestOidcUser.with("sub-42", "John", "john@example.com", "pic", "tok-42");

        assertThat(controller.getToken(user, null)).isEqualTo("tok-42");
        assertThat(controller.getToken(user, "sub-42")).isEqualTo("tok-42");
    }


    /**
     * Verifies that the /token endpoint throws a 403 Forbidden
     * when the provided userId does not match the authenticated user.
     */
    @Test
    void token_throwsForbidden_whenUserIdMismatch() {
        var user = TestOidcUser.with("sub-42", "John", "john@example.com", "pic", "tok-42");

        assertThrows(ResponseStatusException.class,
                () -> controller.getToken(user, "different"));
    }

    /**
     * Verifies that the /me endpoint returns a map containing
     * the subject, email, name, and expiry claim from the authenticated JWT.
     */
    @Test
    void me_returnsClaimsFromJwt() {
        var jwt = TestJwt.with("abc", "abc@example.com", "Alice");
        Map<String, Object> claims = controller.me(jwt);

        assertThat(claims.get("sub")).isEqualTo("abc");
        assertThat(claims.get("email")).isEqualTo("abc@example.com");
        assertThat(claims.get("name")).isEqualTo("Alice");
        assertThat(claims).containsKey("expiresAt");
    }

}
