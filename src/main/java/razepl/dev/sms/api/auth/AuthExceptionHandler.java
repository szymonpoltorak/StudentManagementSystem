package razepl.dev.sms.api.auth;

import graphql.GraphQLError;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.ValidationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.graphql.data.method.annotation.GraphQlExceptionHandler;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import razepl.dev.sms.api.auth.constants.AuthMessages;
import razepl.dev.sms.api.auth.interfaces.AuthExceptionInterface;
import razepl.dev.sms.exceptions.AuthManagerInstanceException;
import razepl.dev.sms.exceptions.GraphQLException;
import razepl.dev.sms.exceptions.InvalidTokenException;
import razepl.dev.sms.exceptions.NullArgumentException;
import razepl.dev.sms.exceptions.PasswordValidationException;
import razepl.dev.sms.exceptions.TokenDoesNotExistException;
import razepl.dev.sms.exceptions.TokensUserNotFoundException;
import razepl.dev.sms.exceptions.UserAlreadyExistsException;

import java.util.stream.Collectors;

/**
 * Class created to handle various exceptions that can be thrown in auth endpoints.
 * It implements {@link AuthExceptionInterface}.
 */
@Slf4j
@ControllerAdvice
public class AuthExceptionHandler implements AuthExceptionInterface {
    @Override
    @GraphQlExceptionHandler(ConstraintViolationException.class)
    public final GraphQLError handleConstraintValidationExceptions(ConstraintViolationException exception) {
        String className = exception.getClass().getSimpleName();
        String errorMessage = exception.getConstraintViolations()
                .stream()
                .map(error -> String.format(AuthMessages.ERROR_FORMAT, error.getPropertyPath(), error.getMessage()))
                .collect(Collectors.joining(AuthMessages.ERROR_DELIMITER));

        return buildResponse(errorMessage, className);
    }

    @Override
    @GraphQlExceptionHandler(MethodArgumentNotValidException.class)
    public final GraphQLError handleMethodArgValidExceptions(MethodArgumentNotValidException exception) {
        String className = exception.getClass().getSimpleName();
        String errorMessage = exception.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(error -> String.format(AuthMessages.ERROR_FORMAT, error.getField(), error.getDefaultMessage()))
                .collect(Collectors.joining(AuthMessages.ERROR_DELIMITER));

        return buildResponse(errorMessage, className);
    }

    @Override
    @GraphQlExceptionHandler(PasswordValidationException.class)
    public final GraphQLError handlePasswordValidationException(ValidationException exception) {
        return buildResponse(exception.getMessage(), exception.getClass().getSimpleName());
    }

    @Override
    @GraphQlExceptionHandler(UsernameNotFoundException.class)
    public final GraphQLError handleUserNotFoundException(UsernameNotFoundException exception) {
        return buildResponse(exception.getMessage(), exception.getClass().getSimpleName());
    }

    @Override
    @GraphQlExceptionHandler(AuthManagerInstanceException.class)
    public final GraphQLError handleAuthManagerInstanceException(InstantiationException exception) {
        return buildResponse(exception.getMessage(), exception.getClass().getSimpleName());
    }

    @Override
    @GraphQlExceptionHandler({InvalidTokenException.class, TokenDoesNotExistException.class, NullArgumentException.class})
    public final GraphQLError handleTokenExceptions(IllegalArgumentException exception) {
        return buildResponse(exception.getMessage(), exception.getClass().getSimpleName());
    }

    @Override
    @GraphQlExceptionHandler({UserAlreadyExistsException.class})
    public final GraphQLError handleUserExistException(UserAlreadyExistsException exception) {
        return buildResponse(exception.getMessage(), exception.getClass().getSimpleName());
    }

    @Override
    @GraphQlExceptionHandler(TokensUserNotFoundException.class)
    public final GraphQLError handleTokenExceptions(TokensUserNotFoundException exception) {
        String className = exception.getClass().getSimpleName();

        return buildResponse(exception.getMessage(), className);
    }

    private GraphQLError buildResponse(String errorMessage, String className) {
        log.error("Exception class name : {}\nError message : {}", className, errorMessage);

        return new GraphQLException(errorMessage, className);
    }
}
