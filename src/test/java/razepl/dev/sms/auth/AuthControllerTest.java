package razepl.dev.sms.auth;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import razepl.dev.sms.auth.data.LoginRequest;
import razepl.dev.sms.auth.data.RegisterRequest;
import razepl.dev.sms.auth.interfaces.AuthController;
import razepl.dev.sms.documents.token.interfaces.TokenRepository;
import razepl.dev.sms.documents.user.User;
import razepl.dev.sms.documents.user.interfaces.UserRepository;
import razepl.dev.sms.exceptions.NullArgumentException;
import razepl.dev.sms.util.AuthTestUtil;

import java.util.List;
import java.util.Optional;

import static razepl.dev.sms.constants.ApiRequests.LOGIN_REQUEST;
import static razepl.dev.sms.constants.ApiRequests.REGISTER_REQUEST;

@SpringBootTest
@AutoConfigureMockMvc
class AuthControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private AuthController authInterface;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TokenRepository tokenRepository;

    @AfterEach
    final void cleanUp() {
        userRepository.deleteAll();
        tokenRepository.deleteAll();
    }

    @BeforeEach
    final void init() {
        List<GrantedAuthority> authorities = List.of(new SimpleGrantedAuthority("ROLE_USER"));
        Authentication authentication = new UsernamePasswordAuthenticationToken("username", "password", authorities);

        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

    @Test
    final void test_registerUser_successful_register() throws Exception {
        // given
        String password = "Abc1!l1.DKk";
        RegisterRequest request = AuthTestUtil.createUserForRegister(password);

        // when
        mockMvc.perform(MockMvcRequestBuilders.post(REGISTER_REQUEST)
                        .content(AuthTestUtil.asJsonString(request))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .with(SecurityMockMvcRequestPostProcessors.csrf()))
                .andExpect(MockMvcResultMatchers.status().isCreated());

        Optional<User> result = userRepository.findByName(request.name());

        // then
        Assertions.assertNotNull(result, "Registering user has failed!");
    }

    @ParameterizedTest
    @CsvSource({
            "Abcdla.dkk",
            "Abc1al1dDKk",
            "Abca!ln.DKk",
            "a",
            "ABCDEFGHIJK",
            "abcdefghijk"
    })
    final void test_registerUser_parametrized(String password) throws Exception {
        // given
        Optional<User> expected = Optional.empty();
        RegisterRequest request = AuthTestUtil.createUserForRegister(password);

        // when
        mockMvc.perform(MockMvcRequestBuilders.post(REGISTER_REQUEST)
                        .content(AuthTestUtil.asJsonString(request))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .with(SecurityMockMvcRequestPostProcessors.csrf()))
                .andExpect(MockMvcResultMatchers.status().isUnprocessableEntity());

        Optional<User> result = userRepository.findByName(request.name());

        // then
        Assertions.assertEquals(expected, result, "Registering user has failed!");
    }

    @Test
    final void test_registerUser() throws Exception {
        // given
        String password = "Abc1!l1.DKk";
        RegisterRequest request = AuthTestUtil.createUserForRegister(password);

        // when
        mockMvc.perform(MockMvcRequestBuilders.post(REGISTER_REQUEST)
                .content(AuthTestUtil.asJsonString(request))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .with(SecurityMockMvcRequestPostProcessors.csrf()));

        User user = userRepository.findByName(request.name()).orElseThrow();

        boolean result = tokenRepository.findAllValidTokensByUserId(user.getUserId()).isEmpty();

        // then
        Assertions.assertFalse(result, "Token was not found!");
    }

    @Test
    final void test_loginUser() throws Exception {
        // given
        String password = "Abc1!l1.DKk";
        RegisterRequest request = AuthTestUtil.createUserForRegister(password);
        LoginRequest loginRequest = LoginRequest.builder()
                .username(request.email())
                .password(request.password())
                .build();

        // when
        mockMvc.perform(MockMvcRequestBuilders.post(REGISTER_REQUEST)
                        .content(AuthTestUtil.asJsonString(request))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .with(SecurityMockMvcRequestPostProcessors.csrf()))
                .andExpect(MockMvcResultMatchers.status().isCreated());

        mockMvc.perform(MockMvcRequestBuilders.post(LOGIN_REQUEST)
                        .content(AuthTestUtil.asJsonString(loginRequest))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .with(SecurityMockMvcRequestPostProcessors.csrf()))
                .andExpect(MockMvcResultMatchers.status().isOk());

        // then
    }

    @Test
    final void test_loginUser_args_nulls() {
        // given

        // when

        // then
        Assertions.assertThrows(NullArgumentException.class, () -> authInterface.loginUser(null));
    }

    @Test
    final void test_registerUser_args_nulls() {
        // given

        // when

        // then
        Assertions.assertThrows(NullArgumentException.class, () -> authInterface.registerUser(null));
    }

    @Test
    final void test_refreshUserToken_args_nulls() {
        // given

        // when

        // then
        Assertions.assertThrows(NullArgumentException.class, () -> authInterface.refreshUserToken(null));
    }
}
