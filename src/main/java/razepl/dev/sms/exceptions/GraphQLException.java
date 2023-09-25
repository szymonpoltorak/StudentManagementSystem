package razepl.dev.sms.exceptions;

import graphql.ErrorClassification;
import graphql.GraphQLError;
import graphql.language.SourceLocation;

import java.io.Serial;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class GraphQLException extends RuntimeException implements GraphQLError {
    @Serial
    private static final long serialVersionUID = 5176760917647011572L;

    private final String className;

    public GraphQLException(String message, String className) {
        super(message);

        this.className = className;
    }

    @Override
    public final Map<String, Object> getExtensions() {
        return Collections.singletonMap("ClassName", className);
    }

    @Override
    public final List<SourceLocation> getLocations() {
        return Collections.emptyList();
    }

    @Override
    public final ErrorClassification getErrorType() {
        return null;
    }
}
