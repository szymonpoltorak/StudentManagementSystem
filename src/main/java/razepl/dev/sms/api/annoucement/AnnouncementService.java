package razepl.dev.sms.api.annoucement;

import razepl.dev.sms.documents.announcement.AnnouncementDto;

import java.util.List;

public interface AnnouncementService {
    List<AnnouncementDto> getListOfAnnouncements();
}
