package razepl.dev.sms.api.annoucement;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import razepl.dev.sms.api.annoucement.interfaces.AnnouncementService;
import razepl.dev.sms.documents.announcement.Announcement;
import razepl.dev.sms.documents.announcement.AnnouncementDto;
import razepl.dev.sms.documents.announcement.AnnouncementMapper;
import razepl.dev.sms.documents.announcement.AnnouncementRepository;

import java.util.Collections;
import java.util.List;

import static razepl.dev.sms.api.annoucement.constants.AnnouncementsConstants.SIZE_OF_PAGE;

@Slf4j
@Service
@RequiredArgsConstructor
public class AnnouncementServiceImpl implements AnnouncementService {
    private final AnnouncementMapper announcementMapper;
    private final AnnouncementRepository announcementRepository;

    @Override
    public final List<AnnouncementDto> getListOfAnnouncements(int numberOfPage) {
        if (numberOfPage < 0) {
            return Collections.emptyList();
        }
        Pageable pageable = PageRequest.of(numberOfPage, SIZE_OF_PAGE);
        Page<Announcement> announcements = announcementRepository.findAnnouncementsBy(pageable);

        log.info("Getting list of announcements");

        return announcements
                .stream()
                .sorted()
                .map(announcementMapper::toDto)
                .toList();
    }
}
