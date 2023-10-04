package razepl.dev.sms.api.annoucement.data;

import lombok.Builder;

@Builder
public record UpdateRequest(String announcementId, String title, String content) {
}
