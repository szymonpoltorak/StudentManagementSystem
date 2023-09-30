package razepl.dev.sms.api.auth;

import jakarta.validation.ConstraintViolationException;
import jakarta.validation.ValidationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import razepl.dev.sms.api.auth.constants.AuthMessages;
import razepl.dev.sms.api.auth.data.ExceptionResponse;
import razepl.dev.sms.api.auth.interfaces.AuthExceptionInterface;
import razepl.dev.sms.exceptions.AuthManagerInstanceException;
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
@RestControllerAdvice
public class AuthExceptionHandler implements AuthExceptionInterface {
    @Override
    @ExceptionHandler(ConstraintViolationException.class)
    public final ResponseEntity<ExceptionResponse> handleConstraintValidationExceptions(ConstraintViolationException exception) {
        String className = exception.getClass().getSimpleName();
        String errorMessage = exception.getConstraintViolations()
                .stream()
                .map(error -> String.format(AuthMessages.ERROR_FORMAT, error.getPropertyPath(), error.getMessage()))
                .collect(Collectors.joining(AuthMessages.ERROR_DELIMITER));

        return buildResponse(errorMessage, className, HttpStatus.UNPROCESSABLE_ENTITY);
    }

    @Override
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public final ResponseEntity<ExceptionResponse> handleMethodArgValidExceptions(MethodArgumentNotValidException exception) {
        String className = exception.getClass().getSimpleName();
        String errorMessage = exception.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(error -> String.format(AuthMessages.ERROR_FORMAT, error.getField(), error.getDefaultMessage()))
                .collect(Collectors.joining(AuthMessages.ERROR_DELIMITER));

        return buildResponse(errorMessage, className, HttpStatus.UNPROCESSABLE_ENTITY);
    }

    @Override
    @ExceptionHandler(PasswordValidationException.class)
    public final ResponseEntity<ExceptionResponse> handlePasswordValidationException(ValidationException exception) {
        return buildResponse(exception.getMessage(), exception.getClass().getSimpleName(), HttpStatus.UNPROCESSABLE_ENTITY);
    }

    @Override
    @ExceptionHandler(UsernameNotFoundException.class)
    public final ResponseEntity<ExceptionResponse> handleUserNotFoundException(UsernameNotFoundException exception) {
        return buildResponse(exception.getMessage(), exception.getClass().getSimpleName(), HttpStatus.NOT_FOUND);
    }

    @Override
    @ExceptionHandler(AuthManagerInstanceException.class)
    public final ResponseEntity<ExceptionResponse> handleAuthManagerInstanceException(InstantiationException exception) {
        return buildResponse(exception.getMessage(), exception.getClass().getSimpleName(), HttpStatus.FAILED_DEPENDENCY);
    }

    @Override
    @ExceptionHandler({InvalidTokenException.class, TokenDoesNotExistException.class, NullArgumentException.class})
    public final ResponseEntity<ExceptionResponse> handleTokenExceptions(IllegalArgumentException exception) {
        return buildResponse(exception.getMessage(), exception.getClass().getSimpleName(), HttpStatus.UNAUTHORIZED);
    }

    @Override
    @ExceptionHandler({UserAlreadyExistsException.class})
    public final ResponseEntity<ExceptionResponse> handleUserExistException(UserAlreadyExistsException exception) {
        return buildResponse(exception.getMessage(), exception.getClass().getSimpleName(), HttpStatus.UNPROCESSABLE_ENTITY);
    }

    @Override
    @ExceptionHandler(TokensUserNotFoundException.class)
    public final ResponseEntity<ExceptionResponse> handleTokenExceptions(TokensUserNotFoundException exception) {
        String className = exception.getClass().getSimpleName();

        return buildResponse(exception.getMessage(), className, HttpStatus.UNAUTHORIZED);
    }

    private ResponseEntity<ExceptionResponse> buildResponse(String errorMessage, String className, HttpStatus statusCode) {
        ExceptionResponse exceptionResponse = ExceptionResponse
                .builder()
                .exceptionClassName(className)
                .errorMessage(errorMessage)
                .build();

        log.error("Exception class name : {}\nError message : {}", className, errorMessage);

        return new ResponseEntity<>(exceptionResponse, statusCode);
    }
}
