package razepl.dev.sms.auth.data;

import lombok.Builder;

/**
 * The LoginRequest class is a model class representing a login request.
 */
@Builder
public record LoginRequest(String username, String password) {
}
