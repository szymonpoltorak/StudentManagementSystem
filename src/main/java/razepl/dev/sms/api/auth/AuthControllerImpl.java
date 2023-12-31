package razepl.dev.sms.api.auth;

import lombok.RequiredArgsConstructor;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ResponseStatus;
import razepl.dev.sms.api.auth.data.AuthResponse;
import razepl.dev.sms.api.auth.data.LoginRequest;
import razepl.dev.sms.api.auth.data.RegisterRequest;
import razepl.dev.sms.api.auth.data.TokenRequest;
import razepl.dev.sms.api.auth.data.TokenResponse;
import razepl.dev.sms.api.auth.interfaces.AuthController;
import razepl.dev.sms.api.auth.interfaces.AuthService;

import static razepl.dev.sms.api.auth.constants.AuthMappings.AUTHENTICATE_MAPPING;
import static razepl.dev.sms.api.auth.constants.AuthMappings.LOGIN_MAPPING;
import static razepl.dev.sms.api.auth.constants.AuthMappings.REFRESH_MAPPING;
import static razepl.dev.sms.api.auth.constants.AuthMappings.REGISTER_MAPPING;

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