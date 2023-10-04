package razepl.dev.sms.exceptions.auth;

import java.io.Serial;

public class NegativeIdException extends IllegalArgumentException {
    @Serial
    private static final long serialVersionUID = 1987088589023261882L;

    public NegativeIdException(String message) {
        super(message);
    }
}
