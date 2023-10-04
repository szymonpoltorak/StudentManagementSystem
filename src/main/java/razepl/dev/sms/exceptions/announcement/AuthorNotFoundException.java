package razepl.dev.sms.exceptions.announcement;

import java.io.Serial;

public class AuthorNotFoundException extends RuntimeException {
    @Serial
    private static final long serialVersionUID = -8367759392716760493L;

    public AuthorNotFoundException(String message) {
        super(message);
    }
}
