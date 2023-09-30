package razepl.dev.sms.exceptions.auth;

import java.io.Serial;

/**
 * Exception for the situation when requested JWT token does not exist in database.
 */
public class TokenDoesNotExistException extends IllegalArgumentException {
    @Serial
    private static final long serialVersionUID = 2587800456765032886L;

    public TokenDoesNotExistException(String message) {
        super(message);
    }
}
