package razepl.dev.sms.api.annoucement.interfaces;

import razepl.dev.sms.api.annoucement.data.AnnouncementDto;
import razepl.dev.sms.api.annoucement.data.AnnouncementRequest;
import razepl.dev.sms.api.annoucement.data.UpdateRequest;
import razepl.dev.sms.documents.user.User;

import java.util.List;

public interface AnnouncementController {
    List<AnnouncementDto> getListOfAnnouncements(int numberOfPage, User user);

    AnnouncementDto addNewAnnouncement(AnnouncementRequest announcementRequest, User author);

    AnnouncementDto removeAnnouncement(String announcementId, User user);

    AnnouncementDto updateAnnouncement(UpdateRequest updateRequest, User user);
}
