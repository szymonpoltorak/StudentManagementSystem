package razepl.dev.sms.documents.announcement;

import lombok.Builder;

@Builder
public record AnnouncementDto(String title, String content, String dateTime, String authorName) {
}
