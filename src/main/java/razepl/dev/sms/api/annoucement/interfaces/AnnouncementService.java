package razepl.dev.sms.api.annoucement.interfaces;

import razepl.dev.sms.documents.announcement.AnnouncementDto;

import java.util.List;

@FunctionalInterface
public interface AnnouncementService {
    List<AnnouncementDto> getListOfAnnouncements(int numberOfPage);
}
