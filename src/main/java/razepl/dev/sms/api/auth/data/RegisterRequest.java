package razepl.dev.sms.api.auth.data;

import lombok.Builder;

/**
 * The RegisterRequest class is a model class representing a user registration request.
 */
@Builder
public record RegisterRequest(String name, String surname, String email, String password, String dateOfBirth) {
}
