package razepl.dev.sms.auth;

import graphql.ErrorClassification;
import graphql.GraphQLError;
import graphql.language.SourceLocation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.ValidationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.graphql.data.method.annotation.GraphQlExceptionHandler;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import razepl.dev.sms.auth.interfaces.AuthExceptionInterface;
import razepl.dev.sms.exceptions.*;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static razepl.dev.sms.auth.constants.AuthMessages.ERROR_DELIMITER;
import static razepl.dev.sms.auth.constants.AuthMessages.ERROR_FORMAT;

/**
 * Class created to handle various exceptions that can be thrown in auth endpoints.
 * It implements {@link AuthExceptionInterface}.
 */
@Slf4j
@ControllerAdvice
public class AuthExceptionHandler implements AuthExceptionInterface {
    @Override
    @GraphQlExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(value = HttpStatus.UNPROCESSABLE_ENTITY)
    public final GraphQLError handleConstraintValidationExceptions(ConstraintViolationException exception) {
        String className = exception.getClass().getSimpleName();
        String errorMessage = exception.getConstraintViolations()
                .stream()
                .map(error -> String.format(ERROR_FORMAT, error.getPropertyPath(), error.getMessage()))
                .collect(Collectors.joining(ERROR_DELIMITER));

        log.error("Exception class name : {}\nError message : {}", className, errorMessage);

        return buildResponse(errorMessage, className);
    }

    @Override
    @GraphQlExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(value = HttpStatus.UNPROCESSABLE_ENTITY)
    public final GraphQLError handleMethodArgValidExceptions(MethodArgumentNotValidException exception) {
        String className = exception.getClass().getSimpleName();
        String errorMessage = exception.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(error -> String.format(ERROR_FORMAT, error.getField(), error.getDefaultMessage()))
                .collect(Collectors.joining(ERROR_DELIMITER));

        log.error("Exception class name : {}\nError message : {}", className, errorMessage);

        return buildResponse(errorMessage, className);
    }

    @Override
    @GraphQlExceptionHandler(PasswordValidationException.class)
    @ResponseStatus(value = HttpStatus.UNPROCESSABLE_ENTITY)
    public final GraphQLError handlePasswordValidationException(ValidationException exception) {
        return buildResponse(exception.getMessage(), exception.getClass().getSimpleName());
    }

    @Override
    @GraphQlExceptionHandler(UsernameNotFoundException.class)
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    public final GraphQLError handleUserNotFoundException(UsernameNotFoundException exception) {
        return buildResponse(exception.getMessage(), exception.getClass().getSimpleName());
    }

    @Override
    @GraphQlExceptionHandler(AuthManagerInstanceException.class)
    @ResponseStatus(value = HttpStatus.FAILED_DEPENDENCY)
    public final GraphQLError handleAuthManagerInstanceException(InstantiationException exception) {
        return buildResponse(exception.getMessage(), exception.getClass().getSimpleName());
    }

    @Override
    @GraphQlExceptionHandler({InvalidTokenException.class, TokenDoesNotExistException.class, NullArgumentException.class})
    @ResponseStatus(value = HttpStatus.UNAUTHORIZED)
    public final GraphQLError handleTokenExceptions(IllegalArgumentException exception) {
        return buildResponse(exception.getMessage(), exception.getClass().getSimpleName());
    }

    @Override
    @GraphQlExceptionHandler({UserAlreadyExistsException.class})
    @ResponseStatus(value = HttpStatus.UNPROCESSABLE_ENTITY)
    public final GraphQLError handleUserExistException(UserAlreadyExistsException exception) {
        return buildResponse(exception.getMessage(), exception.getClass().getSimpleName());
    }

    @Override
    @GraphQlExceptionHandler(TokensUserNotFoundException.class)
    @ResponseStatus(value = HttpStatus.UNAUTHORIZED)
    public final GraphQLError handleTokenExceptions(TokensUserNotFoundException exception) {
        String className = exception.getClass().getSimpleName();

        log.error("Exception class name : {}\nError message : {}", className, exception.getMessage());

        return buildResponse(exception.getMessage(), className);
    }

    private GraphQLError buildResponse(String errorMessage, String className) {
        return new GraphQLError() {
            @Override
            public String getMessage() {
                return errorMessage;
            }

            @Override
            public Map<String, Object> getExtensions() {
                return Collections.singletonMap("ClassName", className);
            }

            @Override
            public List<SourceLocation> getLocations() {
                return null;
            }

            @Override
            public ErrorClassification getErrorType() {
                return null;
            }
        };
    }
}
