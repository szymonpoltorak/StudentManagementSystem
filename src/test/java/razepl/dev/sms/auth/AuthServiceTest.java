package razepl.dev.sms.auth;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import razepl.dev.sms.auth.data.AuthResponse;
import razepl.dev.sms.auth.data.LoginRequest;
import razepl.dev.sms.auth.data.RegisterRequest;
import razepl.dev.sms.auth.data.TokenRequest;
import razepl.dev.sms.config.jwt.interfaces.JwtService;
import razepl.dev.sms.config.jwt.interfaces.TokenManagerService;
import razepl.dev.sms.documents.token.interfaces.TokenRepository;
import razepl.dev.sms.documents.user.User;
import razepl.dev.sms.documents.user.interfaces.UserRepository;
import razepl.dev.sms.exceptions.*;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@SpringBootTest
@AutoConfigureMockMvc
class AuthServiceTest {
    @Mock
    private UserRepository userRepository;

    @Mock
    private TokenRepository tokenRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private TokenManagerService tokenManager;

    @Mock
    private JwtService jwtService;

    @InjectMocks
    private AuthServiceImpl authService;

    private User user;

    private RegisterRequest registerUserRequest;

    private LoginRequest loginUserRequest;

    @BeforeEach
    final void setUp() {
        user = User.builder()
                .name("John")
                .surname("Doe")
                .email("john.doe@example.com")
                .dateOfBirth(LocalDate.of(1990, 1, 1))
                .password("hashedPassword")
                .build();

        registerUserRequest = RegisterRequest.builder()
                .name("John")
                .surname("Doe")
                .email("john.doe@example.com")
                .dateOfBirth(String.valueOf(LocalDate.of(1990, 1, 1)))
                .password("plinPword123123#?!")
                .build();

        loginUserRequest = LoginRequest.builder()
                .username("john.doe@example.com")
                .password("plainPassword")
                .build();
    }

    @Test
    final void test_register_should_save_user_and_return_tokens() {
        // given
        when(userRepository.findByEmail(anyString())).thenReturn(Optional.empty());
        when(passwordEncoder.encode(anyString())).thenReturn("hashedPassword");
        when(tokenManager.buildTokensIntoResponse(any(User.class), anyBoolean())).thenReturn(AuthResponse.builder().build());

        // when
        AuthResponse authResponse = authService.register(registerUserRequest);

        // then
        assertNotNull(authResponse);
        verify(userRepository).save(any(User.class));
        verify(tokenManager).buildTokensIntoResponse(any(User.class), eq(false));
    }

    @Test
    final void test_register_should_throw_exception_if_password_is_invalid() {
        // given
        RegisterRequest registerRequest = RegisterRequest.builder()
                .name("John")
                .surname("Doe")
                .email("john.doe@example.com")
                .dateOfBirth(String.valueOf(LocalDate.of(1990, 1, 1)))
                .password("nope")
                .build();

        // when

        // then
        assertThrows(PasswordValidationException.class, () -> authService.register(registerRequest));
        verify(userRepository, never()).save(any(User.class));
        verify(tokenManager, never()).buildTokensIntoResponse(any(User.class), anyBoolean());
    }

    @Test
    final void test_register_should_throw_exception_if_user_already_exists() {
        // given
        when(userRepository.findByEmail(anyString())).thenReturn(Optional.of(user));

        // when and then
        assertThrows(UserAlreadyExistsException.class, () -> authService.register(registerUserRequest));
        verify(userRepository, never()).save(any(User.class));
        verify(tokenManager, never()).buildTokensIntoResponse(any(User.class), anyBoolean());
    }

    @Test
    final void test_login_should_authenticate_user_and_return_tokens() {
        // given
        when(userRepository.findByEmail(anyString())).thenReturn(Optional.of(user));
        when(authenticationManager.authenticate(any(Authentication.class))).thenReturn(null);
        when(tokenManager.buildTokensIntoResponse(any(User.class), anyBoolean())).thenReturn(AuthResponse.builder().build());

        // when
        AuthResponse authResponse = authService.login(loginUserRequest);

        // then
        assertNotNull(authResponse);
        verify(authenticationManager).authenticate(any(Authentication.class));
        verify(tokenManager).buildTokensIntoResponse(any(User.class), eq(true));
    }

    @Test
    final void test_login_should_throw_exception_if_user_does_not_exist() {
        // given
        when(userRepository.findByEmail(anyString())).thenReturn(Optional.empty());

        // when

        // then
        assertThrows(UsernameNotFoundException.class, () -> authService.login(loginUserRequest));
        verify(tokenManager, never()).buildTokensIntoResponse(any(User.class), anyBoolean());
    }

    @Test
    final void test_refreshToken_should_return_new_tokens_if_refresh_token_is_valid() {
        // given
        String refreshToken = "refreshToken";

        when(jwtService.getUsernameFromToken("refreshToken")).thenReturn("john.doe@example.com");

        when(userRepository.findByEmail(anyString())).thenReturn(Optional.of(user));
        when(jwtService.isTokenValid("refreshToken", user)).thenReturn(true);
        when(jwtService.generateToken(user)).thenReturn("authToken");
        when(tokenManager.buildTokensIntoResponse(anyString(), anyString())).thenReturn(AuthResponse.builder().build());

        // when
        AuthResponse authResponse = authService.refreshToken(refreshToken);

        // then
        assertNotNull(authResponse);
        verify(tokenManager).revokeUserTokens(user);
        verify(tokenManager).saveUsersToken("authToken", user);
        verify(tokenManager).buildTokensIntoResponse("authToken", "refreshToken");
    }

    @Test
    final void test_refreshToken_should_throw_exception_if_refresh_token_does_not_exist() {
        // given
        String refreshToken = null;

        // when

        // then
        assertThrows(NullArgumentException.class, () -> authService.refreshToken(refreshToken));
        verify(tokenManager, never()).revokeUserTokens(any(User.class));
        verify(tokenManager, never()).saveUsersToken(anyString(), any(User.class));
        verify(tokenManager, never()).buildTokensIntoResponse(anyString(), anyString());
    }

    @Test
    final void test_refreshToken_should_throw_exception_if_user_does_not_exist() {
        // given
        String refreshToken = "refreshToken";

        when(jwtService.getUsernameFromToken("refreshToken")).thenReturn("john.doe@example.com");

        when(userRepository.findByEmail(anyString())).thenReturn(Optional.empty());

        // when

        // then
        assertThrows(UsernameNotFoundException.class, () -> authService.refreshToken(refreshToken));
        verify(tokenManager, never()).revokeUserTokens(any(User.class));
        verify(tokenManager, never()).saveUsersToken(anyString(), any(User.class));
        verify(tokenManager, never()).buildTokensIntoResponse(anyString(), anyString());
    }

    @Test
    final void test_refreshToken_should_throw_exception_if_refresh_token_is_invalid() {
        // given
        String refreshToken = "refreshToken";

        when(jwtService.getUsernameFromToken("refreshToken")).thenReturn("john.doe@example.com");

        when(userRepository.findByEmail(anyString())).thenReturn(Optional.of(user));
        when(jwtService.isTokenValid("refreshToken", user)).thenReturn(false);

        // when

        // then
        assertThrows(InvalidTokenException.class, () -> authService.refreshToken(refreshToken));
        verify(tokenManager, never()).revokeUserTokens(any(User.class));
        verify(tokenManager, never()).saveUsersToken(anyString(), any(User.class));
        verify(tokenManager, never()).buildTokensIntoResponse(anyString(), anyString());
    }

    @Test
    final void test_validateUsersTokens_not_existing_tokens() {
        // given
        TokenRequest request = TokenRequest.builder()
                .authToken("")
                .build();

        // when

        // then
        Assertions.assertThrows(TokensUserNotFoundException.class, () -> authService.validateUsersTokens(request));
    }

    @Test
    final void test_validateUsersTokens_nulls() {
        // given

        // when

        // then
        Assertions.assertThrows(NullArgumentException.class, () -> authService.validateUsersTokens(null));
    }

    @Test
    final void test_loginUser_args_nulls() {
        // given

        // when

        // then
        Assertions.assertThrows(NullArgumentException.class, () -> authService.login(null));
    }

    @Test
    final void test_registerUser_args_nulls() {
        // given

        // when

        // then
        Assertions.assertThrows(NullArgumentException.class, () -> authService.register(null));
    }

    @Test
    final void test_refreshUserToken_args_nulls() {
        // given

        // when

        // then
        Assertions.assertThrows(NullArgumentException.class, () -> authService.refreshToken(null));
    }

    @Test
    final void test_register_wrong_password() {
        // given
        RegisterRequest request = new RegisterRequest("name", "surname", "email",
                "password", String.valueOf(LocalDate.now()));

        // when

        // then
        Assertions.assertThrows(PasswordValidationException.class, () -> authService.register(request));
    }
}
