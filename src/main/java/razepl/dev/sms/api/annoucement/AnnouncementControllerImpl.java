package razepl.dev.sms.api.annoucement;

import lombok.RequiredArgsConstructor;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;
import razepl.dev.sms.api.annoucement.interfaces.AnnouncementController;
import razepl.dev.sms.api.annoucement.interfaces.AnnouncementService;
import razepl.dev.sms.documents.announcement.AnnouncementDto;

import java.util.List;

import static razepl.dev.sms.api.annoucement.constants.AnnouncementsMappings.LIST_OF_ANNOUNCEMENTS_MAPPING;

@Controller
@RequiredArgsConstructor
public class AnnouncementControllerImpl implements AnnouncementController {
    private final AnnouncementService announcementService;

    @Override
    @QueryMapping(value = LIST_OF_ANNOUNCEMENTS_MAPPING)
    public final List<AnnouncementDto> getListOfAnnouncements(@Argument int numberOfPage) {
        return announcementService.getListOfAnnouncements(numberOfPage);
    }
}
