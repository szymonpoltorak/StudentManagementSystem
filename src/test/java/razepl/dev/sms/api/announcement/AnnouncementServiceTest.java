package razepl.dev.sms.api.announcement;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import razepl.dev.sms.api.annoucement.AnnouncementServiceImpl;
import razepl.dev.sms.documents.announcement.Announcement;
import razepl.dev.sms.documents.announcement.AnnouncementDto;
import razepl.dev.sms.documents.announcement.AnnouncementMapper;
import razepl.dev.sms.documents.announcement.AnnouncementRepository;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@SpringBootTest
class AnnouncementServiceTest {
    @InjectMocks
    private AnnouncementServiceImpl announcementService;

    @Mock
    private AnnouncementRepository announcementRepository;

    @Mock
    private AnnouncementMapper announcementMapper;

    private Announcement announcement1;

    private Announcement announcement2;

    private AnnouncementDto announcement1Dto;

    private AnnouncementDto announcement2Dto;

    @BeforeEach
    final void setUp() {
        announcement1 = Announcement
                .builder()
                .dateTime(LocalDateTime.parse("2021-01-01T12:00:00"))
                .title("title")
                .content("content")
                .authorName("authorName")
                .build();

        announcement2 = Announcement
                .builder()
                .title("title2")
                .dateTime(LocalDateTime.parse("2023-01-01T12:00:00"))
                .content("content2")
                .authorName("authorName1")
                .build();

        announcement1Dto = AnnouncementDto
                .builder()
                .dateTime("2021-01-01T12:00:00")
                .title("title")
                .content("content")
                .authorName("authorName")
                .build();

        announcement2Dto = AnnouncementDto
                .builder()
                .title("title2")
                .dateTime("2023-01-01T12:00:00")
                .content("content2")
                .authorName("authorName1")
                .build();
    }

    @Test
    final void test_getListOfAnnouncements_shouldReturnListOfAnnouncements() {
        // given
        List<AnnouncementDto> expected = List.of(announcement1Dto, announcement2Dto);

        when(announcementRepository.findAllByOrderByDateTimeDesc())
                .thenReturn(List.of(announcement1, announcement2));
        when(announcementMapper.toDto(announcement1))
                .thenReturn(announcement1Dto);
        when(announcementMapper.toDto(announcement2))
                .thenReturn(announcement2Dto);

        // when
        List<AnnouncementDto> result = announcementService.getListOfAnnouncements();

        // then
        assertEquals(expected, result,
                String.format("Method should have returned : \n%s, \nbut returned : \n%s", expected, result));
    }
}
