package razepl.dev.sms.api.annoucement.interfaces;

import graphql.GraphQLError;

public interface AnnouncementExceptionHandler {
    GraphQLError handleAuthorNotFoundException(RuntimeException exception);

    GraphQLError handleAnnouncementNotFoundException(RuntimeException exception);
}
