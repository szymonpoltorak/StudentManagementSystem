package razepl.dev.sms.auth;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Service;
import razepl.dev.sms.ArgumentValidator;
import razepl.dev.sms.documents.token.JwtToken;
import razepl.dev.sms.documents.token.interfaces.TokenRepository;
import razepl.dev.sms.exceptions.TokenDoesNotExistException;

import static razepl.dev.sms.config.constants.Headers.*;

/**
 * Service class for logging user out.
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class LogoutService implements LogoutHandler {
    private final TokenRepository tokenRepository;

    @Override
    public final void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        ArgumentValidator.throwIfNull(request, response, authentication);

        String authHeader = request.getHeader(AUTH_HEADER);

        if (authHeader == null || !authHeader.startsWith(TOKEN_HEADER)) {
            log.warn("Auth header is null or it does not contain Bearer token");

            return;
        }
        String jwt = authHeader.substring(TOKEN_START_INDEX);
        
        JwtToken token = tokenRepository.findByToken(jwt).orElseThrow(
                () -> new TokenDoesNotExistException("Jwt in header: {}\nToken is null")
        );

        log.info("Jwt in header : {}\nToken is not null", jwt);

        token.setExpired(true);
        token.setRevoked(true);

        tokenRepository.save(token);

        SecurityContextHolder.clearContext();
    }
}
