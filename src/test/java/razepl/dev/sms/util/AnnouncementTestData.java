package razepl.dev.sms.util;

import razepl.dev.sms.documents.announcement.Announcement;
import razepl.dev.sms.api.annoucement.data.AnnouncementDto;

public record AnnouncementTestData(Announcement announcement1, Announcement announcement2, Announcement announcement3,
                                   AnnouncementDto announcement1Dto, AnnouncementDto announcement2Dto,
                                   AnnouncementDto announcement3Dto) {
}
