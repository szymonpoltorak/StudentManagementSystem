package razepl.dev.sms.api.announcement;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import razepl.dev.sms.api.annoucement.AnnouncementServiceImpl;
import razepl.dev.sms.api.annoucement.data.AnnouncementDto;
import razepl.dev.sms.api.annoucement.data.AnnouncementRequest;
import razepl.dev.sms.documents.announcement.Announcement;
import razepl.dev.sms.documents.announcement.interfaces.AnnouncementMapper;
import razepl.dev.sms.documents.announcement.interfaces.AnnouncementRepository;
import razepl.dev.sms.documents.user.User;
import razepl.dev.sms.util.AnnouncementTestData;
import razepl.dev.sms.util.AnnouncementTestDataBuilder;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static razepl.dev.sms.api.annoucement.constants.AnnouncementsConstants.SIZE_OF_PAGE;

@SpringBootTest
class AnnouncementServiceTest {
    private static final String ERROR_MESSAGE_PATTERN =
            "Method should have returned : \n%s, \nbut returned : \n%s";

    @InjectMocks
    private AnnouncementServiceImpl announcementService;

    @Mock
    private AnnouncementRepository announcementRepository;

    @Mock
    private AnnouncementMapper announcementMapper;

    private final AnnouncementTestData testData = AnnouncementTestDataBuilder.getData();

    @Test
    final void test_getListOfAnnouncements_shouldReturnListOfAnnouncements() {
        // given
        final int NUMBER_OF_PAGE = 1;
        List<AnnouncementDto> expected = List.of(testData.announcement1Dto(), testData.announcement2Dto());
        Page<Announcement> expectedPage = new PageImpl<>(List.of(testData.announcement1(), testData.announcement2()));

        when(announcementRepository.findAnnouncementsBy(PageRequest.of(NUMBER_OF_PAGE, SIZE_OF_PAGE)))
                .thenReturn(expectedPage);
        when(announcementMapper.toDto(testData.announcement1()))
                .thenReturn(testData.announcement1Dto());
        when(announcementMapper.toDto(testData.announcement2()))
                .thenReturn(testData.announcement2Dto());

        // when
        List<AnnouncementDto> result = announcementService.getListOfAnnouncements(NUMBER_OF_PAGE);

        // then
        assertEquals(expected, result, String.format(ERROR_MESSAGE_PATTERN, expected, result));
    }

    @Test
    final void test_getListOfAnnouncements_shouldReturnListOfAnnouncementSortedBySameDateAndDifferentHours() {
        // given
        final int NUMBER_OF_PAGE = 1;
        List<AnnouncementDto> expected = List.of(testData.announcement3Dto(), testData.announcement1Dto(),
                testData.announcement2Dto());
        Page<Announcement> expectedPage = new PageImpl<>(List.of(testData.announcement1(),
                testData.announcement2(), testData.announcement3()));

        when(announcementRepository.findAnnouncementsBy(PageRequest.of(NUMBER_OF_PAGE, SIZE_OF_PAGE)))
                .thenReturn(expectedPage);
        when(announcementMapper.toDto(testData.announcement1()))
                .thenReturn(testData.announcement1Dto());
        when(announcementMapper.toDto(testData.announcement2()))
                .thenReturn(testData.announcement2Dto());
        when(announcementMapper.toDto(testData.announcement3()))
                .thenReturn(testData.announcement3Dto());

        // when
        List<AnnouncementDto> result = announcementService.getListOfAnnouncements(NUMBER_OF_PAGE);

        // then
        assertEquals(expected, result, String.format(ERROR_MESSAGE_PATTERN, expected, result));
    }

    @Test
    final void test_getListOfAnnouncements_shouldReturnEmptyListIfNumberOfPageIsNegative() {
        // given
        final int NUMBER_OF_PAGE = -1;
        List<AnnouncementDto> expected = Collections.emptyList();

        // when
        List<AnnouncementDto> result = announcementService.getListOfAnnouncements(NUMBER_OF_PAGE);

        // then
        assertEquals(expected, result, String.format(ERROR_MESSAGE_PATTERN, expected, result));
    }

    @Test
    final void test_addNewAnnouncement_shouldProperlyAddNewAnnouncement() {
        // given
        AnnouncementDto expected = testData.announcement2Dto();


        when(announcementMapper.toDto(any()))
                .thenReturn(testData.announcement2Dto());

        // when
        AnnouncementDto result = announcementService.addNewAnnouncement(testData.announcementRequest(), testData.user());

        // then
        assertEquals(expected, result, String.format(ERROR_MESSAGE_PATTERN, expected, result));
        verify(announcementRepository).save(testData.announcement2());
    }
}
