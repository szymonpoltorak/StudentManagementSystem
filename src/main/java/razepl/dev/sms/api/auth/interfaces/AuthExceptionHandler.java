package razepl.dev.sms.api.auth.interfaces;

import graphql.GraphQLError;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.ValidationException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.MethodArgumentNotValidException;

/**
 * The AuthExceptionInterface interface defines a methods for handling errors in Authentication.
 */
public interface AuthExceptionHandler {
    GraphQLError handleConstraintValidationExceptions(ConstraintViolationException exception);

    GraphQLError handleMethodArgValidExceptions(MethodArgumentNotValidException exception);

    GraphQLError handlePasswordValidationException(ValidationException exception);

    GraphQLError handleUserNotFoundException(UsernameNotFoundException exception);

    GraphQLError handleAuthManagerInstanceException(InstantiationException exception);

    GraphQLError handleTokenExceptions(IllegalArgumentException exception);

    GraphQLError handleUserExistException(IllegalStateException exception);

    GraphQLError handleTokenExceptions(IllegalStateException exception);

    GraphQLError handleBadCredentialsException(BadCredentialsException exception);
}
