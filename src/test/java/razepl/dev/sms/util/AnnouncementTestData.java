package razepl.dev.sms.util;

import razepl.dev.sms.api.annoucement.data.AnnouncementRequest;
import razepl.dev.sms.api.annoucement.data.UpdateRequest;
import razepl.dev.sms.documents.announcement.Announcement;
import razepl.dev.sms.api.annoucement.data.AnnouncementDto;
import razepl.dev.sms.documents.user.User;

public record AnnouncementTestData(Announcement announcement1, Announcement announcement2, Announcement announcement3,
                                   AnnouncementDto announcement1Dto, AnnouncementDto announcement2Dto,
                                   AnnouncementDto announcement3Dto, User user, AnnouncementRequest announcementRequest,
                                   UpdateRequest updateRequest, AnnouncementDto updateRequestDto) {
}
