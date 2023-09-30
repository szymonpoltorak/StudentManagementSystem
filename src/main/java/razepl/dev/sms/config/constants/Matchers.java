package razepl.dev.sms.config.constants;

public final class Matchers {
    /**
     * The mapping for authentication endpoints.
     */
    public static final String AUTH_MAPPING = "/auth/";

    /**
     * The URL for logging out.
     */
    public static final String LOGOUT_URL = "/api/auth/logout";

    public static final String[] WHITE_LIST = {"/graphiql", "/api/auth/**"};

    private Matchers() {
    }
}
