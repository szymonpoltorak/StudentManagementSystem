package razepl.dev.sms.exceptions;

import java.io.Serial;

/**
 * This class represents an exception that is thrown when a user is not found in the tokens list.
 * It is a subclass of IllegalStateException.
 */
public class TokensUserNotFoundException extends IllegalStateException {
    @Serial
    private static final long serialVersionUID = 1780791396069156608L;
}
