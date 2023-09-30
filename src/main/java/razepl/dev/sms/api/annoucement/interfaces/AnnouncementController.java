package razepl.dev.sms.api.annoucement.interfaces;

import razepl.dev.sms.documents.announcement.AnnouncementDto;

import java.util.List;

@FunctionalInterface
public interface AnnouncementController {
    List<AnnouncementDto> getListOfAnnouncements(int numberOfPage);
}
