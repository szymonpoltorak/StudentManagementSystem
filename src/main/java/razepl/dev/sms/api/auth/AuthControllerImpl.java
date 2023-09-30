package razepl.dev.sms.api.auth;

import lombok.RequiredArgsConstructor;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import razepl.dev.sms.api.auth.data.AuthResponse;
import razepl.dev.sms.api.auth.data.LoginRequest;
import razepl.dev.sms.api.auth.data.RegisterRequest;
import razepl.dev.sms.api.auth.data.TokenRequest;
import razepl.dev.sms.api.auth.data.TokenResponse;
import razepl.dev.sms.api.auth.interfaces.AuthController;
import razepl.dev.sms.api.auth.interfaces.AuthService;

import static razepl.dev.sms.api.auth.constants.AuthMappings.AUTHENTICATE_MAPPING;
import static razepl.dev.sms.api.auth.constants.AuthMappings.AUTH_MAPPING;
import static razepl.dev.sms.api.auth.constants.AuthMappings.LOGIN_MAPPING;
import static razepl.dev.sms.api.auth.constants.AuthMappings.REFRESH_MAPPING;
import static razepl.dev.sms.api.auth.constants.AuthMappings.REGISTER_MAPPING;

/**
 * Class to control auth endpoints.
 * It implements {@link AuthController}.
 */
@RestController
@RequiredArgsConstructor
@RequestMapping(value = AUTH_MAPPING)
public class AuthControllerImpl implements AuthController {
    private final AuthService authService;

    @Override
    @PostMapping(value = REGISTER_MAPPING)
    @ResponseStatus(value = HttpStatus.CREATED)
    public final AuthResponse registerUser(@RequestBody RegisterRequest registerRequest) {
        return authService.register(registerRequest);
    }

    @Override
    @PostMapping(value = LOGIN_MAPPING)
    public final AuthResponse loginUser(@RequestBody LoginRequest loginRequest) {
        return authService.login(loginRequest);
    }

    @Override
    @PostMapping(value = REFRESH_MAPPING)
    public final AuthResponse refreshUserToken(@RequestParam String refreshToken) {
        return authService.refreshToken(refreshToken);
    }

    @Override
    @GetMapping(value = AUTHENTICATE_MAPPING)
    public final TokenResponse authenticateUser(@RequestBody TokenRequest tokenRequest) {
        return authService.validateUsersTokens(tokenRequest);
    }
}