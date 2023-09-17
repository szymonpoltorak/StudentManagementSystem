package razepl.dev.sms.api.auth.data;

import lombok.Builder;

@Builder
public record TokenResponse(boolean isAuthTokenValid) {
}
