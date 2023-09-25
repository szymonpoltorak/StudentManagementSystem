package razepl.dev.sms.config.jwt;

import jakarta.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UserDetails;
import razepl.dev.sms.config.jwt.interfaces.JwtService;
import razepl.dev.sms.documents.user.User;
import razepl.dev.sms.exceptions.NullArgumentException;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@SpringBootTest
class JwtServiceTest {
    @Autowired
    private JwtService jwtService;

    @Mock
    private HttpServletRequest mockRequest;

    @BeforeEach
    final void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    final void test_getUsernameFromToken_args_nulls() {
        // given

        // when

        // then
        Assertions.assertThrows(NullArgumentException.class, () -> jwtService.getUsernameFromToken(null));
    }

    @Test
    final void test_getClaimFromToken_args_nulls() {
        // given

        // when

        // then
        Assertions.assertThrows(NullArgumentException.class, () -> jwtService.getClaimFromToken(null, null));
    }

    @Test
    final void test_generateRefreshToken_args_nulls() {
        // given

        // when

        // then
        Assertions.assertThrows(NullArgumentException.class, () -> jwtService.generateRefreshToken(null));
    }

    @Test
    final void test_generateToken_args_nulls() {
        // given

        // when

        // then
        Assertions.assertThrows(NullArgumentException.class, () -> jwtService.generateToken(null));
    }

    @Test
    final void test_generateToken_with_map_args_nulls() {
        // given

        // when

        // then
        Assertions.assertThrows(NullArgumentException.class, () -> jwtService.generateToken(null, null, 0L));
    }

    @Test
    final void test_isTokenValid_args_nulls() {
        // given

        // when

        // then
        Assertions.assertThrows(NullArgumentException.class, () -> jwtService.isTokenValid(null, null));
    }

    @Test
    final void test_getJwtToken_args_nulls() {
        // given

        // when

        // then
        Assertions.assertThrows(NullArgumentException.class, () -> jwtService.getJwtToken(null));
    }

    @Test
    final void test_getJwtRefreshToken_args_nulls() {
        // given

        // when

        // then
        Assertions.assertThrows(NullArgumentException.class, () -> jwtService.getJwtRefreshToken(null));
    }

    @Test
    final void test_getUsernameFromToken_ValidToken_ReturnsUsername() {
        // given
        String token = jwtService.generateToken(User
                .builder()
                .name("john.doe")
                .email("john.doe@gmail.com")
                .password("password")
                .dateOfBirth(LocalDate.now())
                .build());
        String expectedUsername = "john.doe@gmail.com";

        // when
        String username = jwtService.getUsernameFromToken(token);

        // then
        assertEquals(expectedUsername, username);
    }

    @Test
    final void test_generateRefreshToken_ValidUserDetails_ReturnsToken() {
        // given
        UserDetails userDetails = User
                .builder()
                .name("john.doe")
                .email("john.doe@gmail.com")
                .password("password")
                .dateOfBirth(LocalDate.now())
                .build();

        // when
        String refreshToken = jwtService.generateRefreshToken(userDetails);

        // then
        assertNotNull(refreshToken);
    }

    @Test
    final void test_generateToken_ValidUserDetails_ReturnsToken() {
        // given
        UserDetails userDetails = User
                .builder()
                .name("john.doe")
                .email("john.doe@gmail.com")
                .password("password")
                .dateOfBirth(LocalDate.now())
                .build();

        // when
        String token = jwtService.generateToken(userDetails);

        // then
        assertNotNull(token);
    }

    @Test
    final void test_isTokenValid_ValidTokenAndUserDetails_ReturnsTrue() {
        // given
        UserDetails userDetails = User
                .builder()
                .name("john.doe")
                .email("john.doe@gmail.com")
                .password("password")
                .dateOfBirth(LocalDate.now())
                .build();
        String token = jwtService.generateToken(userDetails);

        // when
        boolean isValid = jwtService.isTokenValid(token, userDetails);

        // then
        assertTrue(isValid);
    }

    @Test
    final void test_getJwtToken_ValidHttpServletRequest_ReturnsToken() {
        // given
        String authHeader = "Bearer valid-token";

        // when
        when(mockRequest.getHeader("Authorization")).thenReturn(authHeader);

        // then
        assertThrows(NullArgumentException.class, () -> jwtService.getJwtToken(mockRequest));
    }

    @Test
    final void test_getJwtToken_InvalidHttpServletRequest_ReturnsNull() {
        // given

        // when
        when(mockRequest.getHeader("Authorization")).thenReturn(null);

        // then
        assertThrows(NullArgumentException.class, () -> jwtService.getJwtToken(mockRequest));
    }

    @Test
    final void test_getJwtRefreshToken_ValidHttpServletRequest_ReturnsToken() {
        // given
        String authHeader = "Bearer valid-refresh-token";

        // when
        when(mockRequest.getHeader("Authorization")).thenReturn(authHeader);

        String refreshToken = jwtService.getJwtRefreshToken(mockRequest);

        // then
        assertEquals("valid-refresh-token", refreshToken);
    }

    @Test
    final void test_getJwtRefreshToken_InvalidHttpServletRequest_ReturnsNull() {
        // given

        // when
        when(mockRequest.getHeader("Authorization")).thenReturn(null);

        String refreshToken = jwtService.getJwtRefreshToken(mockRequest);

        // then
        assertNull(refreshToken);
    }
}
