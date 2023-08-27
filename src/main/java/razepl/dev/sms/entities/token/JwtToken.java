package razepl.dev.sms.entities.token;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import razepl.dev.sms.entities.token.interfaces.Token;
import razepl.dev.sms.entities.user.User;

/**
 * Entity class that is mapped for a table in database representing JWT tokens.
 */
@Data
@Builder
@AllArgsConstructor
@Document(collection = "jwt_tokens")
public class JwtToken implements Token {
    @Id
    private long tokenId;

    @NotNull
    private String token;

    @NotNull
    private TokenType tokenType;

    @DBRef
    private User user;

    private boolean isExpired;

    private boolean isRevoked;
}
