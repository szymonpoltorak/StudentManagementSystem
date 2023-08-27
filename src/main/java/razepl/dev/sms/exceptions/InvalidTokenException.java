package razepl.dev.sms.exceptions;

/**
 * Exception thrown when given token is not valid.
 */
public class InvalidTokenException extends IllegalArgumentException {
    public InvalidTokenException(String message) {
        super(message);
    }
}
