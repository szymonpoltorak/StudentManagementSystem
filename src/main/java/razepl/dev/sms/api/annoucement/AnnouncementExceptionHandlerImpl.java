package razepl.dev.sms.api.annoucement;

import graphql.GraphQLError;
import lombok.extern.slf4j.Slf4j;
import org.springframework.graphql.data.method.annotation.GraphQlExceptionHandler;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import razepl.dev.sms.api.annoucement.interfaces.AnnouncementExceptionHandler;
import razepl.dev.sms.exceptions.GraphQLException;
import razepl.dev.sms.exceptions.announcement.AnnouncementNotFoundException;
import razepl.dev.sms.exceptions.announcement.AuthorNotFoundException;

@Slf4j
@ControllerAdvice
public class AnnouncementExceptionHandlerImpl implements AnnouncementExceptionHandler {
    @Override
    @GraphQlExceptionHandler(AuthorNotFoundException.class)
    public final GraphQLError handleAuthorNotFoundException(RuntimeException exception) {
        String className = exception.getClass().getSimpleName();

        return buildResponse(exception.getMessage(), className, HttpStatus.UNAUTHORIZED);
    }

    @Override
    @GraphQlExceptionHandler(AnnouncementNotFoundException.class)
    public final GraphQLError handleAnnouncementNotFoundException(RuntimeException exception) {
        String className = exception.getClass().getSimpleName();

        return buildResponse(exception.getMessage(), className, HttpStatus.NOT_FOUND);
    }

    private GraphQLError buildResponse(String errorMessage, String className, HttpStatus statusCode) {
        log.error("Exception class name : {}\nError message : {}", className, errorMessage);

        return new GraphQLException(errorMessage, className, statusCode);
    }
}
