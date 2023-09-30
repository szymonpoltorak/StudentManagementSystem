package razepl.dev.sms.api.annoucement;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import razepl.dev.sms.documents.announcement.Announcement;
import razepl.dev.sms.documents.announcement.AnnouncementDto;
import razepl.dev.sms.documents.announcement.AnnouncementMapper;
import razepl.dev.sms.documents.announcement.AnnouncementRepository;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class AnnouncementServiceImpl implements AnnouncementService {
    private final AnnouncementMapper announcementMapper;
    private final AnnouncementRepository announcementRepository;

    @Override
    public final List<AnnouncementDto> getListOfAnnouncements() {
        List<Announcement> announcements = announcementRepository.findAllByOrderByDateTimeDesc();

        log.info("Getting list of announcements");

        return announcements
                .stream()
                .map(announcementMapper::toDto)
                .toList();
    }
}
