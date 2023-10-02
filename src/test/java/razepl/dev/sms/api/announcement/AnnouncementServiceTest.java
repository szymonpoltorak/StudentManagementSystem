package razepl.dev.sms.api.announcement;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import razepl.dev.sms.api.annoucement.AnnouncementServiceImpl;
import razepl.dev.sms.api.annoucement.data.AnnouncementDto;
import razepl.dev.sms.api.annoucement.data.AnnouncementRequest;
import razepl.dev.sms.api.annoucement.data.UpdateRequest;
import razepl.dev.sms.documents.announcement.Announcement;
import razepl.dev.sms.documents.announcement.interfaces.AnnouncementMapper;
import razepl.dev.sms.documents.announcement.interfaces.AnnouncementRepository;
import razepl.dev.sms.documents.user.User;
import razepl.dev.sms.exceptions.announcement.AnnouncementNotFoundException;
import razepl.dev.sms.exceptions.announcement.AuthorNotFoundException;
import razepl.dev.sms.util.AnnouncementTestData;
import razepl.dev.sms.util.AnnouncementTestDataBuilder;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
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
        List<AnnouncementDto> result = announcementService.getListOfAnnouncements(NUMBER_OF_PAGE, testData.user());

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
        List<AnnouncementDto> result = announcementService.getListOfAnnouncements(NUMBER_OF_PAGE, testData.user());

        // then
        assertEquals(expected, result, String.format(ERROR_MESSAGE_PATTERN, expected, result));
    }

    @Test
    final void test_getListOfAnnouncements_shouldReturnEmptyListIfNumberOfPageIsNegative() {
        // given
        final int NUMBER_OF_PAGE = -1;
        List<AnnouncementDto> expected = Collections.emptyList();

        // when
        List<AnnouncementDto> result = announcementService.getListOfAnnouncements(NUMBER_OF_PAGE, testData.user());

        // then
        assertEquals(expected, result, String.format(ERROR_MESSAGE_PATTERN, expected, result));
    }

    @Test
    final void test_getListOfAnnouncements_shouldThrowUsernameNotFoundExceptionOnNotAuthenticatedUser() {
        // given
        final int NUMBER_OF_PAGE = -1;

        // when

        // then
        assertThrows(UsernameNotFoundException.class,
                () -> announcementService.getListOfAnnouncements(NUMBER_OF_PAGE, null));
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
        verify(announcementRepository).save(any(Announcement.class));
    }

    @Test
    final void test_addNewAnnouncement_shouldThrowAuthorNotFoundExceptionOnNotAuthenticatedUser() {
        // given
        AnnouncementRequest request = testData.announcementRequest();

        // when

        // then
        assertThrows(AuthorNotFoundException.class,
                () -> announcementService.addNewAnnouncement(request, null));
    }

    @Test
    final void test_removeAnnouncement_shouldProperlyRemoveAnnouncement() {
        // given
        final String ANNOUNCEMENT_ID = "id1";
        AnnouncementDto expected = testData.announcement1Dto();
        Announcement announcement = testData.announcement1();

        when(announcementRepository.findById(ANNOUNCEMENT_ID))
                .thenReturn(Optional.ofNullable(testData.announcement1()));
        when(announcementMapper.toDto(testData.announcement1()))
                .thenReturn(testData.announcement1Dto());

        // when
        AnnouncementDto result = announcementService.removeAnnouncement(ANNOUNCEMENT_ID, testData.user());

        // then
        assertEquals(expected, result, String.format(ERROR_MESSAGE_PATTERN, expected, result));
        assertNotNull(announcement, "Announcement cannot be null fix test data");
        verify(announcementRepository).delete(announcement);
    }

    @Test
    final void test_removeAnnouncement_shouldThrowUsernameNotFoundExceptionOnNotAuthenticatedUser() {
        // given
        String ANNOUNCEMENT_ID = "id1";

        // when

        // then
        assertThrows(UsernameNotFoundException.class,
                () -> announcementService.removeAnnouncement(ANNOUNCEMENT_ID, null));
    }

    @Test
    final void test_removeAnnouncement_shouldThrowAnnouncementNotFoundExceptionOnNull() {
        // given
        String ANNOUNCEMENT_ID = null;
        User user = testData.user();

        // when

        // then
        assertThrows(AnnouncementNotFoundException.class,
                () -> announcementService.removeAnnouncement(ANNOUNCEMENT_ID, user));
    }

    @Test
    final void test_removeAnnouncement_shouldThrowAnnouncementNotFoundException() {
        // given
        final String ANNOUNCEMENT_ID = "NOT_EXISTING_ID";
        User user = testData.user();

        // when

        // then
        assertThrows(AnnouncementNotFoundException.class,
                () -> announcementService.removeAnnouncement(ANNOUNCEMENT_ID, user));
    }

    @Test
    final void test_updateAnnouncement_shouldThrowUsernameNotFoundExceptionOnNotAuthenticatedUser() {
        // given
        Announcement announcement = testData.announcement1();
        UpdateRequest request = testData.updateRequest();

        // when

        // then
        assertThrows(UsernameNotFoundException.class, () -> announcementService.updateAnnouncement(request, null));
    }

    @Test
    final void test_updateAnnouncement_shouldUpdateTitle() {
        // given
        Announcement announcement = testData.announcement1();
        UpdateRequest updateRequest = testData.updateRequest();
        AnnouncementDto expected = testData.updateRequestDto();

        when(announcementRepository.findById(updateRequest.announcementId()))
                .thenReturn(Optional.of(announcement));
        when(announcementMapper.toDto(announcement))
                .thenReturn(expected);

        // when
        AnnouncementDto result = announcementService.updateAnnouncement(updateRequest, testData.user());

        // then
        assertEquals(expected, result, String.format(ERROR_MESSAGE_PATTERN, expected, result));
        verify(announcementRepository).save(any(Announcement.class));
    }
}
