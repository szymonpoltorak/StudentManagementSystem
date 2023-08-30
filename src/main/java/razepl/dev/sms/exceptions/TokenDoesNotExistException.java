package razepl.dev.sms.exceptions;

/**
 * Exception for the situation when requested JWT token does not exist in database.
 */
public class TokenDoesNotExistException extends IllegalArgumentException {
    public TokenDoesNotExistException(String message) {
        super(message);
    }
}
