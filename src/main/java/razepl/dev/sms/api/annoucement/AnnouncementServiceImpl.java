package razepl.dev.sms.api.annoucement;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import razepl.dev.sms.api.annoucement.data.AnnouncementDto;
import razepl.dev.sms.api.annoucement.data.AnnouncementRequest;
import razepl.dev.sms.api.annoucement.data.UpdateRequest;
import razepl.dev.sms.api.annoucement.interfaces.AnnouncementService;
import razepl.dev.sms.documents.announcement.Announcement;
import razepl.dev.sms.documents.announcement.interfaces.AnnouncementMapper;
import razepl.dev.sms.documents.announcement.interfaces.AnnouncementRepository;
import razepl.dev.sms.documents.user.User;
import razepl.dev.sms.exceptions.announcement.AnnouncementNotFoundException;
import razepl.dev.sms.exceptions.announcement.AuthorNotFoundException;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

import static razepl.dev.sms.api.annoucement.constants.AnnouncementsConstants.SIZE_OF_PAGE;

@Slf4j
@Service
@RequiredArgsConstructor
public class AnnouncementServiceImpl implements AnnouncementService {
    private final AnnouncementMapper announcementMapper;
    private final AnnouncementRepository announcementRepository;

    @Override
    public final List<AnnouncementDto> getListOfAnnouncements(int numberOfPage, User user) {
        validateUsersAuthentication(user);

        if (numberOfPage < 0) {
            log.warn("User gave the negative number of page!");

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

    @Override
    public final AnnouncementDto addNewAnnouncement(AnnouncementRequest announcementRequest, User author) {
        if (author == null) {
            log.error("Author was not found in methods body!");

            throw new AuthorNotFoundException("User have to be authenticated using jwt token!");
        }
        log.info("Announcement request : {}", announcementRequest);
        log.info("User : {}, added new announcement!", author);

        Announcement announcement = Announcement
                .builder()
                .authorName(author.getFullName())
                .time(LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm", Locale.UK)))
                .date(LocalDate.now())
                .content(announcementRequest.content())
                .title(announcementRequest.title())
                .build();
        log.info(String.valueOf(announcement));

        announcement = announcementRepository.save(announcement);

        log.info("Saved announcement : {}", announcement);

        return announcementMapper.toDto(announcement);
    }

    @Override
    public final AnnouncementDto removeAnnouncement(String announcementId, User user) {
        validateUsersAuthentication(user);

        log.info("Removing the announcement with id : {}", announcementId);
        log.info("User who wants to remove announcement : {}", user.getEmail());

        Announcement announcementToRemove = announcementRepository.findById(announcementId)
                .orElseThrow(() -> new AnnouncementNotFoundException
                        (String.format("User with id %s was not found!", announcementId))
                );
        announcementRepository.delete(announcementToRemove);

        log.info("Announcement has been removed");

        return announcementMapper.toDto(announcementToRemove);
    }

    @Override
    public final AnnouncementDto updateAnnouncement(UpdateRequest updateRequest, User user) {
        validateUsersAuthentication(user);

        log.info("Received update request with data : {}", updateRequest);
        log.info("User who wants to update : {}", user.getEmail());

        Announcement announcement = announcementRepository.findById(updateRequest.announcementId())
                .orElseThrow(() -> new AnnouncementNotFoundException(
                        String.format("Announcement of id '%s' does not exist!", updateRequest.announcementId())
                ));

        log.info("Announcement from database : {}", announcement);

        announcement.update(updateRequest);

        log.info("New announcement : {}", announcement);

        announcementRepository.save(announcement);

        return announcementMapper.toDto(announcement);
    }

    private void validateUsersAuthentication(User user) {
        if (user == null) {
            log.error("User was not authenticated!");

            throw new UsernameNotFoundException("User is not authenticated!");
        }
    }
}
