package razepl.dev.sms.api.auth.interfaces;

import jakarta.validation.ConstraintViolationException;
import jakarta.validation.ValidationException;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import razepl.dev.sms.api.auth.data.ExceptionResponse;
import razepl.dev.sms.exceptions.auth.TokensUserNotFoundException;
import razepl.dev.sms.exceptions.auth.UserAlreadyExistsException;

/**
 * The AuthExceptionInterface interface defines a methods for handling errors in Authentication.
 */
public interface AuthExceptionInterface {
    ResponseEntity<ExceptionResponse> handleConstraintValidationExceptions(ConstraintViolationException exception);

    ResponseEntity<ExceptionResponse> handleMethodArgValidExceptions(MethodArgumentNotValidException exception);

    ResponseEntity<ExceptionResponse> handlePasswordValidationException(ValidationException exception);

    ResponseEntity<ExceptionResponse> handleUserNotFoundException(UsernameNotFoundException exception);

    ResponseEntity<ExceptionResponse> handleAuthManagerInstanceException(InstantiationException exception);

    ResponseEntity<ExceptionResponse> handleTokenExceptions(IllegalArgumentException exception);

    ResponseEntity<ExceptionResponse> handleUserExistException(UserAlreadyExistsException exception);

    ResponseEntity<ExceptionResponse> handleTokenExceptions(TokensUserNotFoundException exception);
}
