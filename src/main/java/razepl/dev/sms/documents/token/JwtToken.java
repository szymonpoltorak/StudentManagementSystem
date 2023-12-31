package razepl.dev.sms.documents.token;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import razepl.dev.sms.documents.user.User;

/**
 * Entity class that is mapped for a table in database representing JWT tokens.
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "jwt_tokens")
public class JwtToken {
    @Id
    private String tokenId;

    @NotNull
    private String token;

    @NotNull
    private TokenType tokenType;

    @DBRef
    private User user;

    private boolean isExpired;

    private boolean isRevoked;
}
