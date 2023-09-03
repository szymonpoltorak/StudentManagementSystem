package razepl.dev.sms.auth.interfaces;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import razepl.dev.sms.auth.data.*;

/**
 * This interface provides methods for user authentication and authorization.
 */
public interface AuthService {
    /**
     * Registers a user with the provided registration request data.
     *
     * @param userRequest The registration request data.
     * @return An AuthResponse object with authentication and refresh tokens.
     */
    AuthResponse register(RegisterRequest userRequest);

    /**
     * Logs a user in with the provided login request data.
     *
     * @param loginRequest The login request data.
     * @return An AuthResponse object with authentication and refresh tokens.
     */
    AuthResponse login(LoginRequest loginRequest);

    /**
     * Refreshes a user's authentication token using their refresh token.
     *
     * @param refreshToken jwt refresh token.
     * @return An AuthResponse object with the new authentication and refresh tokens.
     */
    AuthResponse refreshToken(String refreshToken);

    /**
     * Validates the user's tokens using the given token request.
     *
     * @param request the token request containing the user's tokens
     * @return a token response containing information about the validity of the tokens
     */
    TokenResponse validateUsersTokens(TokenRequest request);
}

