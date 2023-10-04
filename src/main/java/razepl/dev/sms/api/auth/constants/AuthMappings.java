package razepl.dev.sms.api.auth.constants;

/**
 * A utility class that contains constants for authentication mappings.
 */
public final class AuthMappings {
    /**
     * The mapping for user registration endpoint.
     */
    public static final String REGISTER_MAPPING = "registerUser";

    public static final String AUTH_MAPPING = "/api/auth";

    /**
     * The mapping for user login endpoint.
     */
    public static final String LOGIN_MAPPING = "loginUser";

    /**
     * The mapping for user login endpoint.
     */
    public static final String REFRESH_MAPPING = "refreshUserToken";

    public static final String AUTHENTICATE_MAPPING = "authenticateUser";

    /**
     * A private constructor to prevent instantiation of this class.
     */
    private AuthMappings() {
    }
}
