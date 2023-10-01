package razepl.dev.sms.util;

import org.mapstruct.factory.Mappers;
import razepl.dev.sms.api.annoucement.data.AnnouncementDto;
import razepl.dev.sms.api.annoucement.data.AnnouncementRequest;
import razepl.dev.sms.documents.announcement.Announcement;
import razepl.dev.sms.documents.announcement.interfaces.AnnouncementMapper;
import razepl.dev.sms.documents.user.User;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public final class AnnouncementTestDataBuilder {
    private static final AnnouncementMapper MAPPER = Mappers.getMapper(AnnouncementMapper.class);

    private AnnouncementTestDataBuilder() {
    }

    public static AnnouncementTestData getData() {
        Announcement announcement1 = getAnnouncement(LocalDate.parse("2021-01-01"), "12:00",
                "title", "content", "authorName 1", "id1");

        Announcement announcement2 = getAnnouncement(LocalDate.now(),
                LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm", Locale.UK)), "title2",
                "content2", "authorName 2", "id2");

        Announcement announcement3 = getAnnouncement(LocalDate.parse("2021-01-01"), "11:59", "title3",
                "content3", "authorName 3", "id3");

        AnnouncementDto announcement1Dto = MAPPER.toDto(announcement1);

        AnnouncementDto announcement2Dto = MAPPER.toDto(announcement2);

        AnnouncementDto announcement3Dto = MAPPER.toDto(announcement3);

        AnnouncementRequest announcementRequest = AnnouncementRequest
                .builder()
                .content("content2")
                .title("title2")
                .build();
        User user = User
                .builder()
                .email("user@email.com")
                .name("authorName")
                .surname("2")
                .build();
        return new AnnouncementTestData(announcement1, announcement2, announcement3,
                announcement1Dto, announcement2Dto, announcement3Dto, user, announcementRequest);
    }

    private static Announcement getAnnouncement(LocalDate date, String time, String title,
                                                String content, String authorName, String id) {
        return Announcement
                .builder()
                .id(id)
                .date(date)
                .time(time)
                .title(title)
                .content(content)
                .authorName(authorName)
                .build();
    }
}
