package razepl.dev.sms.auth.constants;

/**
 * The AuthMessages class contains constant string messages related to user authentication.
 * These messages are used to provide feedback to users during the authentication process.
 */
public final class AuthMessages {
    /**
     * Delimiter string used to separate error messages.
     */
    public static final String ERROR_DELIMITER = "\n";

    /**
     * Format string used to construct error messages. This string takes two arguments:
     * the error code and the error message.
     */
    public static final String ERROR_FORMAT = "%s : %s";

    /**
     * Private constructor to prevent instantiation of the AuthMessages class.
     */
    private AuthMessages() {
    }
}
