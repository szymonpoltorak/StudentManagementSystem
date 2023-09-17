package razepl.dev.sms.auth;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.web.servlet.MockMvc;
import razepl.dev.sms.api.auth.interfaces.AuthController;
import razepl.dev.sms.documents.token.interfaces.TokenRepository;
import razepl.dev.sms.documents.user.interfaces.UserRepository;
import razepl.dev.sms.exceptions.NullArgumentException;

import java.util.List;

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
