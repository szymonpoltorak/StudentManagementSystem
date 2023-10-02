package razepl.dev.sms.api.annoucement;

import lombok.RequiredArgsConstructor;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import razepl.dev.sms.api.annoucement.data.AnnouncementDto;
import razepl.dev.sms.api.annoucement.data.AnnouncementRequest;
import razepl.dev.sms.api.annoucement.data.UpdateRequest;
import razepl.dev.sms.api.annoucement.interfaces.AnnouncementController;
import razepl.dev.sms.api.annoucement.interfaces.AnnouncementService;
import razepl.dev.sms.documents.user.User;

import java.util.List;

import static razepl.dev.sms.api.annoucement.constants.AnnouncementsMappings.ADD_NEW_ANNOUNCEMENT;
import static razepl.dev.sms.api.annoucement.constants.AnnouncementsMappings.LIST_OF_ANNOUNCEMENTS_MAPPING;
import static razepl.dev.sms.api.annoucement.constants.AnnouncementsMappings.REMOVE_ANNOUNCEMENT;
import static razepl.dev.sms.api.annoucement.constants.AnnouncementsMappings.UPDATE_ANNOUNCEMENT;

@Controller
@RequiredArgsConstructor
public class AnnouncementControllerImpl implements AnnouncementController {
    private final AnnouncementService announcementService;

    @Override
    @QueryMapping(value = LIST_OF_ANNOUNCEMENTS_MAPPING)
    public final List<AnnouncementDto> getListOfAnnouncements(@Argument int numberOfPage,
                                                              @AuthenticationPrincipal User user) {
        return announcementService.getListOfAnnouncements(numberOfPage, user);
    }

    @Override
    @MutationMapping(value = ADD_NEW_ANNOUNCEMENT)
    public final AnnouncementDto addNewAnnouncement(@Argument AnnouncementRequest announcementRequest,
                                                    @AuthenticationPrincipal User author) {
        return announcementService.addNewAnnouncement(announcementRequest, author);
    }

    @Override
    @MutationMapping(value = REMOVE_ANNOUNCEMENT)
    public final AnnouncementDto removeAnnouncement(@Argument String announcementId,
                                                    @AuthenticationPrincipal User user) {
        return announcementService.removeAnnouncement(announcementId, user);
    }

    @Override
    @MutationMapping(value = UPDATE_ANNOUNCEMENT)
    public final AnnouncementDto updateAnnouncement(@Argument UpdateRequest updateRequest,
                                                    @AuthenticationPrincipal User user) {
        return announcementService.updateAnnouncement(updateRequest, user);
    }
}
