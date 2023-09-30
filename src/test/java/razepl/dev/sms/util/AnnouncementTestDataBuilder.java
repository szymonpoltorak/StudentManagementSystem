package razepl.dev.sms.util;

import razepl.dev.sms.documents.announcement.Announcement;
import razepl.dev.sms.documents.announcement.AnnouncementDto;

import java.time.LocalDate;
import java.time.LocalTime;

public final class AnnouncementTestDataBuilder {
    private AnnouncementTestDataBuilder() {
    }

    public static AnnouncementTestData getData() {
        Announcement announcement1 = Announcement
                .builder()
                .date(LocalDate.parse("2021-01-01"))
                .time(LocalTime.parse("12:00"))
                .title("title")
                .content("content")
                .authorName("authorName")
                .build();

        Announcement announcement2 = Announcement
                .builder()
                .title("title2")
                .date(LocalDate.parse("2023-01-01"))
                .time(LocalTime.parse("12:00"))
                .content("content2")
                .authorName("authorName1")
                .build();

        Announcement announcement3 = Announcement
                .builder()
                .date(LocalDate.parse("2021-01-01"))
                .time(LocalTime.parse("11:59"))
                .title("title3")
                .content("content3")
                .authorName("authorName3")
                .build();

        AnnouncementDto announcement1Dto = AnnouncementDto
                .builder()
                .date("2021-01-01")
                .title("title")
                .content("content")
                .time("12:00")
                .authorName("authorName")
                .build();

        AnnouncementDto announcement2Dto = AnnouncementDto
                .builder()
                .title("title2")
                .time("12:00")
                .date("2023-01-01")
                .content("content2")
                .authorName("authorName1")
                .build();

        AnnouncementDto announcement3Dto = AnnouncementDto
                .builder()
                .date("2021-01-01")
                .time("11:59")
                .title("title3")
                .content("content3")
                .authorName("authorName3")
                .build();
        return new AnnouncementTestData(announcement1, announcement2, announcement3,
                announcement1Dto, announcement2Dto, announcement3Dto);
    }
}
