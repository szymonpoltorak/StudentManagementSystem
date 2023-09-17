package razepl.dev.sms.api.auth.interfaces;

import graphql.GraphQLError;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.ValidationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import razepl.dev.sms.exceptions.TokensUserNotFoundException;
import razepl.dev.sms.exceptions.UserAlreadyExistsException;

/**
 * The AuthExceptionInterface interface defines a methods for handling errors in Authentication.
 */
public interface AuthExceptionInterface {
    GraphQLError handleConstraintValidationExceptions(ConstraintViolationException exception);

    GraphQLError handleMethodArgValidExceptions(MethodArgumentNotValidException exception);

    GraphQLError handlePasswordValidationException(ValidationException exception);

    GraphQLError handleUserNotFoundException(UsernameNotFoundException exception);

    GraphQLError handleAuthManagerInstanceException(InstantiationException exception);

    GraphQLError handleTokenExceptions(IllegalArgumentException exception);

    GraphQLError handleUserExistException(UserAlreadyExistsException exception);

    GraphQLError handleTokenExceptions(TokensUserNotFoundException exception);
}
