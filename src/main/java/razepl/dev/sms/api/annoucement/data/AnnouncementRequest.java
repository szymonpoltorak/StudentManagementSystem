package razepl.dev.sms.api.annoucement.data;

import lombok.Builder;

@Builder
public record AnnouncementRequest(String content, String title) {
}
