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

    /**
     * Swagger json address.
     */
    private static final String SWAGGER_JSON = "/v3/api-docs";
    /**
     * Swagger json matching urls.
     */
    private static final String SWAGGER_JSON_MATCHERS = "/v3/api-docs/**";
    /**
     * Swagger UI url.
     */
    private static final String SWAGGER_UI = "/swagger-ui.html";
    /**
     * Swagger UI matching urls.
     */
    private static final String SWAGGER_UI_MATCHERS = "/swagger-ui/**";
    /**
     * List of URLs that should be excluded from authentication requirements.
     */
    public static final String[] WHITE_LIST = {SWAGGER_JSON, SWAGGER_JSON_MATCHERS, SWAGGER_UI, SWAGGER_UI_MATCHERS,
            "/graphql", "/graphiql"};

    private Matchers() {
    }
}
