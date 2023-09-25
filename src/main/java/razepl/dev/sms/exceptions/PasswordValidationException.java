package razepl.dev.sms.exceptions;

import jakarta.validation.ValidationException;

import java.io.Serial;

/**
 * Exception meaning that password provided by the user does not meet requirements.
 */
public class PasswordValidationException extends ValidationException {
    @Serial
    private static final long serialVersionUID = -1121647642063199592L;

    public PasswordValidationException(String message) {
        super(message);
    }
}
