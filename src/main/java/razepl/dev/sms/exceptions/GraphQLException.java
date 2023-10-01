package razepl.dev.sms.exceptions;

import graphql.ErrorClassification;
import graphql.GraphQLError;
import graphql.language.SourceLocation;
import org.springframework.http.HttpStatus;

import java.io.Serial;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class GraphQLException extends RuntimeException implements GraphQLError {
    @Serial
    private static final long serialVersionUID = 5176760917647011572L;

    private final String className;

    private final HttpStatus httpStatus;

    public GraphQLException(String message, String className, HttpStatus httpStatus) {
        super(message);

        this.className = className;
        this.httpStatus = httpStatus;
    }

    @Override
    public final Map<String, Object> getExtensions() {
        return Map.of("ClassName", className, "HttpStatus", httpStatus);
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
