package razepl.dev.sms.exceptions.auth;

import java.io.Serial;

/**
 * Exception thrown when given token is not valid.
 */
public class InvalidTokenException extends IllegalArgumentException {
    @Serial
    private static final long serialVersionUID = 7403611279828718675L;

    public InvalidTokenException(String message) {
        super(message);
    }
}
