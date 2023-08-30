package razepl.dev.sms.auth.data;

import lombok.Builder;

@Builder
public record TokenResponse(boolean isAuthTokenValid) {
}
