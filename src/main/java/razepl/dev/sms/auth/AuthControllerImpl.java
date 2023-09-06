package razepl.dev.sms.auth;

import lombok.RequiredArgsConstructor;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ResponseStatus;
import razepl.dev.sms.auth.data.*;
import razepl.dev.sms.auth.interfaces.AuthController;
import razepl.dev.sms.auth.interfaces.AuthService;

import static razepl.dev.sms.auth.constants.AuthMappings.*;

/**
 * Class to control auth endpoints.
 * It implements {@link AuthController}.
 */
@Controller
@RequiredArgsConstructor
public class AuthControllerImpl implements AuthController {
    private final AuthService authService;

    @Override
    @MutationMapping(value = REGISTER_MAPPING)
    @ResponseStatus(value = HttpStatus.CREATED)
    public final AuthResponse registerUser(@Argument RegisterRequest registerRequest) {
        return authService.register(registerRequest);
    }

    @Override
    @MutationMapping(value = LOGIN_MAPPING)
    public final AuthResponse loginUser(@Argument LoginRequest loginRequest) {
        return authService.login(loginRequest);
    }

    @Override
    @MutationMapping(value = REFRESH_MAPPING)
    public final AuthResponse refreshUserToken(@Argument String refreshToken) {
        return authService.refreshToken(refreshToken);
    }

    @Override
    @QueryMapping(value = AUTHENTICATE_MAPPING)
    public final TokenResponse authenticateUser(@Argument TokenRequest tokenRequest) {
        return authService.validateUsersTokens(tokenRequest);
    }
}