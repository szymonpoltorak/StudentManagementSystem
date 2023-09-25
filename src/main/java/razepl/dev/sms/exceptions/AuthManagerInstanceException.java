package razepl.dev.sms.exceptions;

import java.io.Serial;

/**
 * Exception indicating that auth manager bean could not be instantiated.
 */
public class AuthManagerInstanceException extends InstantiationException {
    @Serial
    private static final long serialVersionUID = 3208793429293182300L;

    public AuthManagerInstanceException(String message) {
        super(message);
    }
}
