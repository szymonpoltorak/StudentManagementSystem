package razepl.dev.sms.config.constants;

public final class Matchers {
    /**
     * The URL for logging out.
     */
    public static final String LOGOUT_URL = "/api/auth/logout";

    public static final String[] WHITE_LIST = {"/graphiql", "/graphql", "/error"};

    private Matchers() {
    }
}
