package razepl.dev.sms.api.auth.data;

import lombok.Builder;

@Builder
public record TokenRequest(String authToken) {
}
