package razepl.dev.sms.auth.interfaces;

import razepl.dev.sms.auth.data.*;

/**
 * An interface that defines the methods for authentication services.
 */
public interface AuthController {
    /**
     * Registers a user with the provided registration request data.
     *
     * @param registerRequest The registration request data.
     * @return A ResponseEntity containing an {@link AuthResponse} object with authentication and refresh tokens.
     */
    AuthResponse registerUser(RegisterRequest registerRequest);

    /**
     * Logs a user in with the provided login request data.
     *
     * @param loginRequest The login request data.
     * @return A ResponseEntity containing an {@link AuthResponse} object with authentication and refresh tokens.
     */
    AuthResponse loginUser(LoginRequest loginRequest);

    /**
     * Refreshes a user's authentication token using their refresh token.
     *
     * @param refreshToken The jwt refresh token.
     * @return A ResponseEntity containing an {@link AuthResponse} object with the new authentication and refresh tokens.
     */
    AuthResponse refreshUserToken(String refreshToken);

    /**
     * Authenticates a user with the given token request.
     *
     * @param request the token request
     * @return a ResponseEntity with a TokenResponse as the response body
     */
    TokenResponse authenticateUser(TokenRequest request);
}
