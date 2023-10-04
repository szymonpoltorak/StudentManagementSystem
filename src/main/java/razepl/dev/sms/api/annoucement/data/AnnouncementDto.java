package razepl.dev.sms.api.annoucement.data;

import lombok.Builder;

@Builder
public record AnnouncementDto(String title, String content, String date, String time,
                              String authorName, String announcementId) {
}
