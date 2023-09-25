package razepl.dev.sms.config.jwt;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import razepl.dev.sms.ArgumentValidator;
import razepl.dev.sms.api.auth.data.AuthResponse;
import razepl.dev.sms.config.jwt.interfaces.JwtService;
import razepl.dev.sms.config.jwt.interfaces.TokenManagerService;
import razepl.dev.sms.documents.token.JwtToken;
import razepl.dev.sms.documents.token.TokenType;
import razepl.dev.sms.documents.token.interfaces.TokenRepository;
import razepl.dev.sms.documents.user.User;

import java.util.List;

/**
 * Service class to manage building, saving and revoking user tokens.
 * It implements {@link TokenManagerService}.
 */
@Service
@RequiredArgsConstructor
public class TokenManagerServiceImpl implements TokenManagerService {
    private final TokenRepository tokenRepository;
    private final JwtService jwtService;

    @Override
    public final void saveUsersToken(String jwtToken, User user) {
        tokenRepository.save(buildToken(jwtToken, user));
    }

    @Override
    public final AuthResponse refreshUserToken(User user, String authToken, String refreshToken) {
        revokeUserTokens(user);

        saveUsersToken(authToken, user);

        return buildTokensIntoResponse(authToken, refreshToken);
    }

    @Override
    public final AuthResponse buildTokensIntoResponse(String authToken, String refreshToken) {
        return buildResponse(authToken, refreshToken);
    }

    @Override
    public final AuthResponse buildTokensIntoResponse(User user) {
        ArgumentValidator.throwIfNull(user);

        String authToken = jwtService.generateToken(user);
        String refreshToken = jwtService.generateRefreshToken(user);

        saveUsersToken(authToken, user);

        return buildResponse(authToken, refreshToken);
    }

    @Override
    public final void revokeUserTokens(User user) {
        ArgumentValidator.throwIfNull(user);

        List<JwtToken> userTokens = tokenRepository.findAllValidTokensByUserId(user.getUserId());

        if (userTokens.isEmpty()) {
            return;
        }

        userTokens.forEach(token -> {
            token.setRevoked(true);
            token.setExpired(true);
        });
        tokenRepository.saveAll(userTokens);
    }

    private AuthResponse buildResponse(String authToken, String refreshToken) {
        ArgumentValidator.throwIfNull(authToken, refreshToken);

        return AuthResponse.builder()
                .authToken(authToken)
                .refreshToken(refreshToken)
                .build();
    }

    private JwtToken buildToken(String jwtToken, User user) {
        ArgumentValidator.throwIfNull(jwtToken, user);

        return JwtToken.builder()
                .token(jwtToken)
                .tokenType(TokenType.JWT_BEARER_TOKEN)
                .user(user)
                .build();
    }
}
