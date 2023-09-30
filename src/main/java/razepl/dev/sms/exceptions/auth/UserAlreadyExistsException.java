package razepl.dev.sms.exceptions.auth;

import java.io.Serial;

/**
 * This class represents an exception that is thrown when a user already exists.
 * It is a subclass of IllegalStateException.
 */
public class UserAlreadyExistsException extends IllegalStateException {
    @Serial
    private static final long serialVersionUID = -4026563468085254211L;

    /**
     * Constructs a new UserAlreadyExistsException with the specified detail message.
     *
     * @param message The detail message.
     */
    public UserAlreadyExistsException(String message) {
        super(message);
    }
}

